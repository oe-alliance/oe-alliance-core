/*
 * stb-lcdd - small LCDproc protocol bridge for Enigma2 receiver displays
 * Copyright (C) 2026 OpenATV STB Kodi Team
 * SPDX-License-Identifier: GPL-2.0-only
 */

#define _GNU_SOURCE
#include <arpa/inet.h>
#include <errno.h>
#include <fcntl.h>
#include <ft2build.h>
#include FT_FREETYPE_H
#include <hb-ft.h>
#include <hb.h>
#include <linux/fb.h>
#include <linux/types.h>
#include <netinet/in.h>
#include <png.h>
#include <signal.h>
#include <stdbool.h>
#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>
#include <sys/ioctl.h>
#include <sys/mman.h>
#include <sys/select.h>
#include <sys/socket.h>
#include <sys/stat.h>
#include <sys/time.h>
#include <sys/types.h>
#include <time.h>
#include <unistd.h>

#define LCDD_PORT 13666
#define MAX_WIDGETS 96
#define MAX_ARGS 20
#define MAX_LINE 2048
#define MAX_TEXT 1024

#ifndef LCD_IOCTL_ASC_MODE
#define LCDSET 0x1000
#define LCD_IOCTL_ASC_MODE (21 | LCDSET)
#define LCD_MODE_ASC 0
#define LCD_MODE_BIN 1
#endif

#ifndef FBIO_BLIT
#define FBIO_BLIT 0x22
#endif

enum widget_type
{
  WIDGET_NONE,
  WIDGET_SCROLLER,
  WIDGET_HBAR,
  WIDGET_ICON,
  WIDGET_NUM
};

enum pixel_format
{
  PIXEL_NATIVE,
  PIXEL_RGB565_BYTESWAP,
  PIXEL_RGB565_BITORDER,
  PIXEL_DM9X0,
  PIXEL_RGBA,
  PIXEL_ARGB,
  PIXEL_BGRA,
  PIXEL_ABGR,
  PIXEL_FRAMEBUFFER,
  PIXEL_PNG
};

struct widget
{
  char name[64];
  enum widget_type type;
  int x;
  int y;
  int end_x;
  int end_y;
  int width;
  int number;
  int scroll;
  int scroll_speed;
  char scroll_mode;
  char text[MAX_TEXT];
  char icon[32];
};

struct config
{
  char machine[64];
  char mode[32];
  char format[64];
  char device[128];
  int width;
  int height;
  int bpp;
  int columns;
  char secondary_mode[32];
  char secondary_format[64];
  char secondary_device[128];
  int secondary_width;
  int secondary_height;
  int secondary_bpp;
  int secondary_columns;
};

struct display
{
  int fd;
  char device[128];
  bool graphical;
  bool seven_segment;
  bool framebuffer;
  enum pixel_format format;
  int storage_width;
  int width;
  int height;
  int bpp;
  int stride;
  int columns;
  int rows;
  int cell_width;
  int cell_height;
  bool monochrome_text;
  uint8_t *rgb;
  uint8_t *output;
  size_t output_size;
  uint8_t *mapped;
  size_t mapped_size;
  FT_Library ft_library;
  FT_Face ft_face;
  bool ft_ready;
};

struct memory_png
{
  uint8_t *data;
  size_t size;
  size_t capacity;
};

static volatile sig_atomic_t running = 1;
static struct widget widgets[MAX_WIDGETS];
static bool screen_visible = true;

static void signal_stop(int signum)
{
  (void)signum;
  running = 0;
}

static long long monotonic_ms(void)
{
  struct timeval tv;
  gettimeofday(&tv, NULL);
  return (long long)tv.tv_sec * 1000LL + tv.tv_usec / 1000;
}

static void trim(char *value)
{
  char *start = value;
  char *end;
  while (*start == ' ' || *start == '\t' || *start == '\r' || *start == '\n')
    ++start;
  if (start != value)
    memmove(value, start, strlen(start) + 1);
  end = value + strlen(value);
  while (end > value && (end[-1] == ' ' || end[-1] == '\t' || end[-1] == '\r' || end[-1] == '\n'))
    --end;
  *end = '\0';
}

static void load_config(struct config *cfg)
{
  FILE *file;
  char line[512];
  memset(cfg, 0, sizeof(*cfg));
  strcpy(cfg->mode, "none");
  strcpy(cfg->format, "native");
  strcpy(cfg->device, "auto");
  strcpy(cfg->secondary_mode, "none");
  strcpy(cfg->secondary_format, "native");
  strcpy(cfg->secondary_device, "auto");

  file = fopen("/etc/stb-lcdd.conf", "r");
  if (!file)
    return;

  while (fgets(line, sizeof(line), file))
  {
    char *separator = strchr(line, '=');
    char *key;
    char *value;
    if (!separator)
      continue;
    *separator = '\0';
    key = line;
    value = separator + 1;
    trim(key);
    trim(value);
    if (!strcmp(key, "machine"))
      snprintf(cfg->machine, sizeof(cfg->machine), "%s", value);
    else if (!strcmp(key, "mode"))
      snprintf(cfg->mode, sizeof(cfg->mode), "%s", value);
    else if (!strcmp(key, "pixel_format"))
      snprintf(cfg->format, sizeof(cfg->format), "%s", value);
    else if (!strcmp(key, "device"))
      snprintf(cfg->device, sizeof(cfg->device), "%s", value);
    else if (!strcmp(key, "width"))
      cfg->width = atoi(value);
    else if (!strcmp(key, "height"))
      cfg->height = atoi(value);
    else if (!strcmp(key, "bpp"))
      cfg->bpp = atoi(value);
    else if (!strcmp(key, "columns"))
      cfg->columns = atoi(value);
    else if (!strcmp(key, "secondary_mode"))
      snprintf(cfg->secondary_mode, sizeof(cfg->secondary_mode), "%s", value);
    else if (!strcmp(key, "secondary_pixel_format"))
      snprintf(cfg->secondary_format, sizeof(cfg->secondary_format), "%s", value);
    else if (!strcmp(key, "secondary_device"))
      snprintf(cfg->secondary_device, sizeof(cfg->secondary_device), "%s", value);
    else if (!strcmp(key, "secondary_width"))
      cfg->secondary_width = atoi(value);
    else if (!strcmp(key, "secondary_height"))
      cfg->secondary_height = atoi(value);
    else if (!strcmp(key, "secondary_bpp"))
      cfg->secondary_bpp = atoi(value);
    else if (!strcmp(key, "secondary_columns"))
      cfg->secondary_columns = atoi(value);
  }
  fclose(file);
}

static void make_secondary_config(const struct config *source, struct config *secondary)
{
  memset(secondary, 0, sizeof(*secondary));
  snprintf(secondary->machine, sizeof(secondary->machine), "%s-secondary", source->machine);
  snprintf(secondary->mode, sizeof(secondary->mode), "%s", source->secondary_mode);
  snprintf(secondary->format, sizeof(secondary->format), "%s", source->secondary_format);
  snprintf(secondary->device, sizeof(secondary->device), "%s", source->secondary_device);
  secondary->width = source->secondary_width;
  secondary->height = source->secondary_height;
  secondary->bpp = source->secondary_bpp;
  secondary->columns = source->secondary_columns;
}

static int read_proc_hex(const char *path)
{
  FILE *file = fopen(path, "r");
  char value[64];
  long parsed;
  if (!file)
    return 0;
  if (!fgets(value, sizeof(value), file))
  {
    fclose(file);
    return 0;
  }
  fclose(file);
  errno = 0;
  parsed = strtol(value, NULL, 16);
  if (errno || parsed <= 0 || parsed > 8192)
    return 0;
  return (int)parsed;
}

static void disable_lcd_live_video(void)
{
  static const char path[] = "/proc/stb/lcd/live_enable";
  int fd = open(path, O_WRONLY | O_CLOEXEC);
  if (fd < 0)
    return;

  /*
   * VU+ color LCD drivers switch this node to "standby" while Enigma2
   * exits.  In that state writes to /dev/dbox/oled0 succeed but the panel
   * remains black.  "disable" disables live-video mirroring and selects
   * the normal graphics buffer, matching Enigma2's active display state.
   */
  (void)write(fd, "disable", sizeof("disable") - 1);
  close(fd);
}

static void reapply_frontpanel_brightness(void)
{
  static const char *paths[] = {
      "/proc/stb/fp/oled_brightness",
      "/proc/stb/lcd/oled_brightness",
      NULL};
  char value[64];
  int index;

  /*
   * Some VU+ drivers blank the OLED when Enigma2 releases it.  Merely
   * switching /dev/dbox/oled0 to binary mode is not sufficient: writing
   * the current brightness back to the proc node activates the panel.
   */
  for (index = 0; paths[index]; ++index)
  {
    int fd = open(paths[index], O_RDONLY | O_CLOEXEC);
    ssize_t length;
    if (fd < 0)
      continue;
    length = read(fd, value, sizeof(value) - 1);
    close(fd);
    if (length <= 0)
      continue;
    value[length] = '\0';
    while (length > 0 && (value[length - 1] == '\n' || value[length - 1] == '\r'))
      value[--length] = '\0';
    if (length <= 0)
      continue;
    fd = open(paths[index], O_WRONLY | O_CLOEXEC);
    if (fd >= 0)
    {
      (void)write(fd, value, (size_t)length);
      close(fd);
    }
    return;
  }
}

static void enable_seven_segment_panel(void)
{
  static const char path[] = "/proc/stb/fp/power4x7on";
  int fd = open(path, O_WRONLY | O_CLOEXEC);
  if (fd < 0)
    return;

  /* OpenATV's 4x7 front-panel API accepts the strings "on" and "off" even
   * though reading the node reports 1 or 0.  Enigma2 may turn it off during
   * its final cleanup, so explicitly select the active output for Kodi. */
  (void)write(fd, "on", 2);
  close(fd);
}

static enum pixel_format parse_pixel_format(const char *name)
{
  if (!strcmp(name, "rgb565-byteswap"))
    return PIXEL_RGB565_BYTESWAP;
  if (!strcmp(name, "rgb565-bitorder"))
    return PIXEL_RGB565_BITORDER;
  if (!strcmp(name, "dm9x0"))
    return PIXEL_DM9X0;
  if (!strcmp(name, "rgba"))
    return PIXEL_RGBA;
  if (!strcmp(name, "argb"))
    return PIXEL_ARGB;
  if (!strcmp(name, "bgra"))
    return PIXEL_BGRA;
  if (!strcmp(name, "abgr"))
    return PIXEL_ABGR;
  if (!strcmp(name, "framebuffer"))
    return PIXEL_FRAMEBUFFER;
  if (!strcmp(name, "png"))
    return PIXEL_PNG;
  return PIXEL_NATIVE;
}

static const char *find_font(void)
{
  static const char *fonts[] = {
      "/usr/share/fonts/truetype/DejaVuSans.ttf",
      "/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf",
      "/usr/share/kodi/media/Fonts/DejaVuSans.ttf",
      "/usr/share/fonts/ttf/DejaVuSans.ttf",
      NULL};
  int index;
  for (index = 0; fonts[index]; ++index)
    if (access(fonts[index], R_OK) == 0)
      return fonts[index];
  return NULL;
}

static int open_configured_device(struct display *display, const struct config *cfg)
{
  static const char *auto_devices[] = {
      "/dev/dbox/oled0", "/dev/dbox/lcd0", "/dev/lcd2", NULL};
  int index;

  if (strcmp(cfg->device, "auto"))
  {
    snprintf(display->device, sizeof(display->device), "%s", cfg->device);
    return open(display->device, O_RDWR | O_CLOEXEC);
  }

  for (index = 0; auto_devices[index]; ++index)
  {
    int fd = open(auto_devices[index], O_RDWR | O_CLOEXEC);
    if (fd >= 0)
    {
      snprintf(display->device, sizeof(display->device), "%s", auto_devices[index]);
      return fd;
    }
  }
  return -1;
}

static bool display_init(struct display *display, const struct config *cfg)
{
  int proc_width;
  int proc_height;
  int proc_bpp;
  int lcd_mode;
  const char *font;
  struct fb_var_screeninfo fb_var;
  struct fb_fix_screeninfo fb_fix;

  memset(display, 0, sizeof(*display));
  display->fd = -1;
  display->format = parse_pixel_format(cfg->format);
  display->seven_segment = !strcmp(cfg->mode, "7segment");
  display->graphical = !strcmp(cfg->mode, "color");
  display->framebuffer = display->format == PIXEL_FRAMEBUFFER;
  if (!strcmp(cfg->mode, "none"))
  {
    fprintf(stderr, "stb-lcdd: machine %s has no configured front display\n", cfg->machine);
    return false;
  }

  display->fd = open_configured_device(display, cfg);
  if (display->fd < 0)
  {
    fprintf(stderr, "stb-lcdd: no front display device: %s\n", strerror(errno));
    return false;
  }

  if (display->framebuffer)
  {
    memset(&fb_var, 0, sizeof(fb_var));
    memset(&fb_fix, 0, sizeof(fb_fix));
    if (ioctl(display->fd, FBIOGET_VSCREENINFO, &fb_var) < 0 ||
        ioctl(display->fd, FBIOGET_FSCREENINFO, &fb_fix) < 0)
    {
      fprintf(stderr, "stb-lcdd: cannot query LCD framebuffer: %s\n", strerror(errno));
      return false;
    }
    display->storage_width = (int)fb_var.xres;
    display->width = (int)fb_var.xres;
    display->height = (int)fb_var.yres;
    display->bpp = (int)fb_var.bits_per_pixel;
    display->stride = (int)fb_fix.line_length;
    display->mapped_size = (size_t)fb_fix.smem_len;
  }
  else
  {
    proc_width = read_proc_hex("/proc/stb/lcd/xres");
    proc_height = read_proc_hex("/proc/stb/lcd/yres");
    proc_bpp = read_proc_hex("/proc/stb/lcd/bpp");
    /* Explicit geometry belongs to the configured device.  This matters on
     * dual-display receivers such as Vu+ Duo2, where /proc/stb/lcd describes
     * the 140x32 OLED while /dev/lcd2 is a separate 400x240 PNG panel. */
    display->storage_width = cfg->width > 0 ? cfg->width : proc_width;
    display->height = cfg->height > 0 ? cfg->height : proc_height;
    display->bpp = cfg->bpp > 0 ? cfg->bpp : proc_bpp;
  }

  if (display->graphical)
  {
    if (display->storage_width <= 0)
      display->storage_width = 240;
    if (display->height <= 0)
      display->height = 80;
    if (display->bpp <= 0)
      display->bpp = 16;
    if (!display->framebuffer)
      display->width = display->storage_width;
    if (display->format == PIXEL_DM9X0 && display->storage_width > 4)
      display->width = display->storage_width - 4;
    if (!display->framebuffer)
      display->stride = display->storage_width * display->bpp / 8;
    display->columns = 20;
    display->rows = 4;
    display->cell_width = display->width / display->columns;
    display->cell_height = display->height / display->rows;
    if (display->cell_width < 4)
      display->cell_width = 4;
    if (display->cell_height < 8)
      display->cell_height = 8;
    /* Small 8-bit front panels (for example the Vu+ Duo2 140x32 OLED)
     * ultimately display only on/off pixels.  Antialiased six-pixel glyphs
     * turn into jagged fragments when the driver thresholds the gray values.
     * Render a hinted, cell-height monochrome glyph for those panels. */
    display->monochrome_text = display->bpp == 8 && display->height <= 64;
    display->rgb = calloc((size_t)display->width * display->height, 3);
    display->output_size = (size_t)display->stride * display->height;
    display->output = calloc(1, display->output_size);
    if (!display->rgb || (!display->output && display->format != PIXEL_PNG))
    {
      fprintf(stderr, "stb-lcdd: display buffer allocation failed\n");
      return false;
    }
    if (display->framebuffer)
    {
      if (display->mapped_size < display->output_size)
      {
        fprintf(stderr, "stb-lcdd: LCD framebuffer is smaller than its visible surface\n");
        return false;
      }
      display->mapped = mmap(NULL, display->mapped_size, PROT_READ | PROT_WRITE,
                             MAP_SHARED, display->fd, 0);
      if (display->mapped == MAP_FAILED)
      {
        display->mapped = NULL;
        fprintf(stderr, "stb-lcdd: cannot map LCD framebuffer: %s\n", strerror(errno));
        return false;
      }
    }
    lcd_mode = LCD_MODE_BIN;
    if (display->format != PIXEL_PNG && !display->framebuffer)
      ioctl(display->fd, LCD_IOCTL_ASC_MODE, &lcd_mode);
    disable_lcd_live_video();
    reapply_frontpanel_brightness();

    font = find_font();
    if (font && FT_Init_FreeType(&display->ft_library) == 0 &&
        FT_New_Face(display->ft_library, font, 0, &display->ft_face) == 0)
    {
      display->ft_ready = true;
      FT_Set_Pixel_Sizes(display->ft_face, 0, (unsigned int)(display->cell_height * 3 / 4));
    }
  }
  else
  {
    display->columns = cfg->columns > 0 && cfg->columns <= 64
                           ? cfg->columns
                           : (display->seven_segment ? 4 : 16);
    display->rows = display->seven_segment ? 1 : 2;
    display->cell_width = 1;
    display->cell_height = 1;
    lcd_mode = LCD_MODE_ASC;
    ioctl(display->fd, LCD_IOCTL_ASC_MODE, &lcd_mode);
    if (display->seven_segment)
      enable_seven_segment_panel();
  }

  fprintf(stderr,
          "stb-lcdd: %s %s, %dx%dx%d, LCDproc %dx%d cells, format %s\n",
          cfg->machine, display->device, display->width, display->height, display->bpp,
          display->columns, display->rows, cfg->format);
  return true;
}

static void display_close(struct display *display)
{
  if (display->mapped)
    munmap(display->mapped, display->mapped_size);
  if (display->fd >= 0)
    close(display->fd);
  if (display->ft_ready)
  {
    FT_Done_Face(display->ft_face);
    FT_Done_FreeType(display->ft_library);
  }
  free(display->rgb);
  free(display->output);
  memset(display, 0, sizeof(*display));
  display->fd = -1;
}

static inline void put_pixel(struct display *display, int x, int y,
                             uint8_t red, uint8_t green, uint8_t blue)
{
  uint8_t *pixel;
  if (x < 0 || x >= display->width || y < 0 || y >= display->height)
    return;
  pixel = display->rgb + ((size_t)y * display->width + x) * 3;
  pixel[0] = red;
  pixel[1] = green;
  pixel[2] = blue;
}

static void fill_rect(struct display *display, int x, int y, int width, int height,
                      uint8_t red, uint8_t green, uint8_t blue)
{
  int row;
  int column;
  for (row = y; row < y + height; ++row)
    for (column = x; column < x + width; ++column)
      put_pixel(display, column, row, red, green, blue);
}

static void draw_big_number(struct display *display, const struct widget *widget)
{
  static const uint8_t segments[10] = {
      0x3f, 0x06, 0x5b, 0x4f, 0x66,
      0x6d, 0x7d, 0x07, 0x7f, 0x6f};
  int x = (widget->x - 1) * display->cell_width;
  int height = display->cell_height * display->rows;
  int width;
  int thickness;
  int margin;
  int horizontal_x;
  int horizontal_width;
  int upper_y;
  int lower_y;
  int vertical_height;
  uint8_t mask;

  if (widget->number == 10)
  {
    int dot = display->cell_width * 2 / 3;
    if (dot > height / 10)
      dot = height / 10;
    if (dot < 2)
      dot = 2;
    x += (display->cell_width - dot) / 2;
    fill_rect(display, x, height / 3 - dot / 2, dot, dot, 255, 255, 255);
    fill_rect(display, x, height * 2 / 3 - dot / 2, dot, dot, 255, 255, 255);
    return;
  }
  if (widget->number < 0 || widget->number > 9)
    return;

  width = display->cell_width * 3;
  if (x + width > display->width)
    width = display->width - x;
  if (width <= 0 || height <= 0)
    return;
  thickness = width / 5;
  if (thickness > height / 14)
    thickness = height / 14;
  if (thickness < 2)
    thickness = 2;
  margin = thickness / 3;
  if (margin < 1)
    margin = 1;
  horizontal_x = x + margin + thickness / 2;
  horizontal_width = width - 2 * margin - thickness;
  upper_y = margin + thickness;
  lower_y = height / 2 + thickness / 2;
  vertical_height = height / 2 - margin - thickness * 3 / 2;
  if (horizontal_width < 1 || vertical_height < 1)
    return;

  mask = segments[widget->number];
  if (mask & 0x01) /* top */
    fill_rect(display, horizontal_x, margin, horizontal_width, thickness, 255, 255, 255);
  if (mask & 0x02) /* upper right */
    fill_rect(display, x + width - margin - thickness, upper_y,
              thickness, vertical_height, 255, 255, 255);
  if (mask & 0x04) /* lower right */
    fill_rect(display, x + width - margin - thickness, lower_y,
              thickness, vertical_height, 255, 255, 255);
  if (mask & 0x08) /* bottom */
    fill_rect(display, horizontal_x, height - margin - thickness,
              horizontal_width, thickness, 255, 255, 255);
  if (mask & 0x10) /* lower left */
    fill_rect(display, x + margin, lower_y,
              thickness, vertical_height, 255, 255, 255);
  if (mask & 0x20) /* upper left */
    fill_rect(display, x + margin, upper_y,
              thickness, vertical_height, 255, 255, 255);
  if (mask & 0x40) /* centre */
    fill_rect(display, horizontal_x, height / 2 - thickness / 2,
              horizontal_width, thickness, 255, 255, 255);
}

static uint32_t next_utf8(const unsigned char **text)
{
  const unsigned char *p = *text;
  uint32_t codepoint;
  if (*p < 0x80)
  {
    *text = p + 1;
    return *p;
  }
  if ((*p & 0xe0) == 0xc0 && (p[1] & 0xc0) == 0x80)
  {
    codepoint = ((uint32_t)(p[0] & 0x1f) << 6) | (p[1] & 0x3f);
    *text = p + 2;
    return codepoint;
  }
  if ((*p & 0xf0) == 0xe0 && (p[1] & 0xc0) == 0x80 && (p[2] & 0xc0) == 0x80)
  {
    codepoint = ((uint32_t)(p[0] & 0x0f) << 12) |
                ((uint32_t)(p[1] & 0x3f) << 6) | (p[2] & 0x3f);
    *text = p + 3;
    return codepoint;
  }
  if ((*p & 0xf8) == 0xf0 && (p[1] & 0xc0) == 0x80 &&
      (p[2] & 0xc0) == 0x80 && (p[3] & 0xc0) == 0x80)
  {
    codepoint = ((uint32_t)(p[0] & 0x07) << 18) |
                ((uint32_t)(p[1] & 0x3f) << 12) |
                ((uint32_t)(p[2] & 0x3f) << 6) | (p[3] & 0x3f);
    *text = p + 4;
    return codepoint;
  }
  *text = p + 1;
  return '?';
}

static void draw_text(struct display *display, int x, int y, int max_width,
                      const char *text, int pixel_size,
                      uint8_t red, uint8_t green, uint8_t blue)
{
  FT_Face face = display->ft_face;
  hb_font_t *font;
  hb_buffer_t *buffer;
  hb_glyph_info_t *glyphs;
  hb_glyph_position_t *positions;
  unsigned int glyph_count;
  unsigned int index;
  int pen_x = x;
  int baseline;
  int total_advance = 0;

  if (!display->ft_ready || !text || !*text)
    return;
  FT_Set_Pixel_Sizes(face, 0, (unsigned int)pixel_size);
  baseline = y + pixel_size;

  font = hb_ft_font_create_referenced(face);
  buffer = hb_buffer_create();
  if (!font || !buffer)
  {
    if (font)
      hb_font_destroy(font);
    if (buffer)
      hb_buffer_destroy(buffer);
    return;
  }
  hb_buffer_add_utf8(buffer, text, -1, 0, -1);
  hb_buffer_guess_segment_properties(buffer);
  hb_shape(font, buffer, NULL, 0);
  glyphs = hb_buffer_get_glyph_infos(buffer, &glyph_count);
  positions = hb_buffer_get_glyph_positions(buffer, &glyph_count);

  for (index = 0; index < glyph_count; ++index)
    total_advance += (int)(positions[index].x_advance >> 6);
  if (HB_DIRECTION_IS_BACKWARD(hb_buffer_get_direction(buffer)) &&
      total_advance > 0 && total_advance < max_width)
    pen_x = x + max_width - total_advance;

  for (index = 0; index < glyph_count; ++index)
  {
    FT_GlyphSlot glyph;
    int gx;
    int gy;
    int row;
    int column;
    if (FT_Load_Glyph(face, glyphs[index].codepoint,
                      FT_LOAD_RENDER | (display->monochrome_text ? FT_LOAD_TARGET_MONO : FT_LOAD_TARGET_NORMAL)))
      continue;
    glyph = face->glyph;
    gx = pen_x + (int)(positions[index].x_offset >> 6) + glyph->bitmap_left;
    gy = baseline - (int)(positions[index].y_offset >> 6) - glyph->bitmap_top;
    if (gx >= x + max_width)
      break;
    for (row = 0; row < (int)glyph->bitmap.rows; ++row)
    {
      for (column = 0; column < (int)glyph->bitmap.width; ++column)
      {
        int target_x = gx + column;
        int target_y = gy + row;
        uint8_t alpha;
        uint8_t *pixel;
        if (target_x < x || target_x >= x + max_width ||
            target_x < 0 || target_x >= display->width ||
            target_y < 0 || target_y >= display->height)
          continue;
        if (glyph->bitmap.pixel_mode == FT_PIXEL_MODE_MONO)
        {
          const uint8_t packed = glyph->bitmap.buffer[row * glyph->bitmap.pitch + column / 8];
          alpha = (packed & (0x80U >> (column & 7))) ? 255 : 0;
        }
        else
          alpha = glyph->bitmap.buffer[row * glyph->bitmap.pitch + column];
        if (!alpha)
          continue;
        pixel = display->rgb + ((size_t)target_y * display->width + target_x) * 3;
        pixel[0] = (uint8_t)((pixel[0] * (255 - alpha) + red * alpha) / 255);
        pixel[1] = (uint8_t)((pixel[1] * (255 - alpha) + green * alpha) / 255);
        pixel[2] = (uint8_t)((pixel[2] * (255 - alpha) + blue * alpha) / 255);
      }
    }
    pen_x += (int)(positions[index].x_advance >> 6);
    if (pen_x >= x + max_width)
      break;
  }
  hb_buffer_destroy(buffer);
  hb_font_destroy(font);
}

static void draw_icon(struct display *display, const struct widget *widget)
{
  int x = (widget->x - 1) * display->cell_width;
  int y = (widget->y - 1) * display->cell_height;
  int size = display->cell_height * 2 / 3;
  int left = x + (display->cell_width - size) / 2;
  int top = y + (display->cell_height - size) / 2;
  int row;
  int column;
  if (widget->x <= 0 || widget->y <= 0 || !strcmp(widget->icon, "BLOCK_FILLED"))
    return;

  if (!strcmp(widget->icon, "PLAY"))
  {
    for (row = 0; row < size; ++row)
      for (column = 0; column <= row / 2; ++column)
        put_pixel(display, left + column, top + row, 255, 255, 255);
  }
  else if (!strcmp(widget->icon, "PAUSE"))
  {
    fill_rect(display, left, top, size / 3, size, 255, 255, 255);
    fill_rect(display, left + size * 2 / 3, top, size / 3, size, 255, 255, 255);
  }
  else if (!strcmp(widget->icon, "FF") || !strcmp(widget->icon, "FR"))
  {
    int direction = !strcmp(widget->icon, "FR") ? -1 : 1;
    int center = left + size / 2;
    for (row = 0; row < size; ++row)
      for (column = 0; column <= row / 3; ++column)
      {
        put_pixel(display, center + direction * column, top + row, 255, 255, 255);
        put_pixel(display, center + direction * (size / 3 + column), top + row, 255, 255, 255);
      }
  }
  else
  {
    fill_rect(display, left, top, size, size, 255, 255, 255);
  }
}

static size_t utf8_offset(const char *text, int characters)
{
  const unsigned char *cursor = (const unsigned char *)text;
  while (*cursor && characters-- > 0)
    (void)next_utf8(&cursor);
  return (size_t)(cursor - (const unsigned char *)text);
}

static int utf8_length(const char *text)
{
  const unsigned char *cursor = (const unsigned char *)text;
  int length = 0;
  while (*cursor)
  {
    (void)next_utf8(&cursor);
    ++length;
  }
  return length;
}

static void draw_widgets(struct display *display)
{
  int index;
  bool have_content = false;
  bool have_numbers = false;
  memset(display->rgb, 0, (size_t)display->width * display->height * 3);
  if (!screen_visible)
    return;

  for (index = 0; index < MAX_WIDGETS; ++index)
  {
    const struct widget *widget = &widgets[index];
    if (widget->type == WIDGET_NUM && widget->x > 0)
    {
      draw_big_number(display, widget);
      have_numbers = true;
      have_content = true;
    }
  }
  if (have_numbers)
    return;

  for (index = 0; index < MAX_WIDGETS; ++index)
  {
    const struct widget *widget = &widgets[index];
    if (widget->type == WIDGET_HBAR && widget->x > 0 && widget->y > 0 && widget->width > 0)
    {
      int x = (widget->x - 1) * display->cell_width;
      int y = (widget->y - 1) * display->cell_height + display->cell_height / 3;
      int width = widget->width;
      if (width > display->width - x)
        width = display->width - x;
      fill_rect(display, x, y, width, display->cell_height / 3, 20, 135, 210);
      have_content = true;
    }
  }
  for (index = 0; index < MAX_WIDGETS; ++index)
  {
    const struct widget *widget = &widgets[index];
    if (widget->type == WIDGET_ICON)
    {
      draw_icon(display, widget);
      if (widget->x > 0)
        have_content = true;
    }
  }
  for (index = 0; index < MAX_WIDGETS; ++index)
  {
    const struct widget *widget = &widgets[index];
    if (widget->type == WIDGET_SCROLLER && widget->x > 0 && widget->y > 0 && widget->text[0])
    {
      char visible[MAX_TEXT];
      int available = widget->end_x >= widget->x ? widget->end_x - widget->x + 1 : display->columns;
      int length = utf8_length(widget->text);
      int start = 0;
      size_t byte_start;
      size_t byte_end;
      int x = (widget->x - 1) * display->cell_width;
      int y = (widget->y - 1) * display->cell_height;
      if (available <= 0)
        available = display->columns;
      if (length > available && widget->scroll_mode != 'n')
        start = widget->scroll % (length + 3);
      byte_start = start < length ? utf8_offset(widget->text, start) : strlen(widget->text);
      byte_end = utf8_offset(widget->text + byte_start, available);
      if (byte_end >= sizeof(visible))
        byte_end = sizeof(visible) - 1;
      memcpy(visible, widget->text + byte_start, byte_end);
      visible[byte_end] = '\0';
      draw_text(display, x, y, available * display->cell_width, visible,
                display->monochrome_text ? display->cell_height : display->cell_height * 3 / 4,
                255, 255, 255);
      have_content = true;
    }
  }

  if (!have_content)
    draw_text(display, display->cell_width, display->cell_height,
              display->width - 2 * display->cell_width, "Kodi",
              display->cell_height, 255, 255, 255);
}

static bool write_all(int fd, const uint8_t *data, size_t size)
{
  size_t written = 0;
  while (written < size)
  {
    ssize_t result = write(fd, data + written, size - written);
    if (result < 0)
    {
      if (errno == EINTR)
        continue;
      return false;
    }
    if (result == 0)
      return false;
    written += (size_t)result;
  }
  return true;
}

static void png_write_memory(png_structp png, png_bytep data, png_size_t length)
{
  struct memory_png *memory = png_get_io_ptr(png);
  if (memory->size + length > memory->capacity)
  {
    size_t capacity = memory->capacity ? memory->capacity * 2 : 65536;
    uint8_t *resized;
    while (capacity < memory->size + length)
      capacity *= 2;
    resized = realloc(memory->data, capacity);
    if (!resized)
      png_error(png, "out of memory");
    memory->data = resized;
    memory->capacity = capacity;
  }
  memcpy(memory->data + memory->size, data, length);
  memory->size += length;
}

static void png_flush_memory(png_structp png)
{
  (void)png;
}

static bool write_png_display(struct display *display)
{
  png_structp png = NULL;
  png_infop info = NULL;
  png_bytep *rows = NULL;
  struct memory_png memory = {0};
  bool result = false;
  int row;
  int fd = -1;

  png = png_create_write_struct(PNG_LIBPNG_VER_STRING, NULL, NULL, NULL);
  if (!png)
    goto done;
  info = png_create_info_struct(png);
  if (!info || setjmp(png_jmpbuf(png)))
    goto done;
  rows = calloc((size_t)display->height, sizeof(*rows));
  if (!rows)
    goto done;
  for (row = 0; row < display->height; ++row)
    rows[row] = display->rgb + (size_t)row * display->width * 3;
  png_set_write_fn(png, &memory, png_write_memory, png_flush_memory);
  png_set_IHDR(png, info, (png_uint_32)display->width, (png_uint_32)display->height,
               8, PNG_COLOR_TYPE_RGB, PNG_INTERLACE_NONE,
               PNG_COMPRESSION_TYPE_DEFAULT, PNG_FILTER_TYPE_DEFAULT);
  png_set_compression_level(png, 1);
  png_set_rows(png, info, rows);
  png_write_png(png, info, PNG_TRANSFORM_IDENTITY, NULL);

  fd = open(display->device, O_WRONLY | O_CLOEXEC);
  if (fd >= 0)
    result = write_all(fd, memory.data, memory.size);

done:
  if (fd >= 0)
    close(fd);
  free(rows);
  free(memory.data);
  if (png)
    png_destroy_write_struct(&png, info ? &info : NULL);
  return result;
}

static bool flush_graphical(struct display *display)
{
  int y;
  int x;
  if (display->format == PIXEL_PNG)
    return write_png_display(display);

  memset(display->output, 0, display->output_size);
  for (y = 0; y < display->height; ++y)
  {
    for (x = 0; x < display->width; ++x)
    {
      const uint8_t *source = display->rgb + ((size_t)y * display->width + x) * 3;
      uint8_t red = source[0];
      uint8_t green = source[1];
      uint8_t blue = source[2];
      int output_x = x;
      uint8_t *target;
      if (display->format == PIXEL_DM9X0)
        output_x += 4;
      target = display->output + (size_t)y * display->stride +
               (size_t)output_x * display->bpp / 8;
      if (display->bpp == 8)
      {
        target[0] = (uint8_t)((red * 77 + green * 150 + blue * 29) >> 8);
      }
      else if (display->bpp == 16)
      {
        uint16_t value;
        if (display->framebuffer)
          value = (uint16_t)(0x8000U | ((red >> 3) << 10) |
                             ((green >> 3) << 5) | (blue >> 3));
        else
          value = (uint16_t)(((red >> 3) << 11) | ((green >> 2) << 5) | (blue >> 3));
        uint8_t low = (uint8_t)value;
        uint8_t high = (uint8_t)(value >> 8);
        if (display->format == PIXEL_RGB565_BYTESWAP)
        {
          target[0] = high;
          target[1] = low;
        }
        else if (display->format == PIXEL_RGB565_BITORDER)
        {
          target[0] = (uint8_t)((low & 0x07) | ((high << 3) & 0xe8));
          target[1] = (uint8_t)((high & 0xe0) | ((low >> 3) & 0x1f));
        }
        else
        {
          target[0] = low;
          target[1] = high;
        }
      }
      else if (display->bpp == 32)
      {
        if (display->format == PIXEL_DM9X0)
        {
          uint32_t src = (uint32_t)blue | ((uint32_t)green << 8) | ((uint32_t)red << 16);
          uint32_t converted = ((src >> 3) & 0x001f001fU) |
                               ((src << 3) & 0xf800f800U) |
                               ((src >> 8) & 0x00e000e0U) |
                               ((src << 8) & 0x07000700U);
          memcpy(target, &converted, sizeof(converted));
        }
        else if (display->format == PIXEL_RGBA)
        {
          target[0] = red; target[1] = green; target[2] = blue; target[3] = 0;
        }
        else if (display->format == PIXEL_ARGB)
        {
          target[0] = 0; target[1] = red; target[2] = green; target[3] = blue;
        }
        else if (display->format == PIXEL_BGRA)
        {
          target[0] = blue; target[1] = green; target[2] = red; target[3] = 0xff;
        }
        else if (display->format == PIXEL_ABGR)
        {
          target[0] = 0; target[1] = blue; target[2] = green; target[3] = red;
        }
        else
        {
          target[0] = blue; target[1] = green; target[2] = red;
          target[3] = display->framebuffer ? 0xff : 0;
        }
      }
    }
  }
  if (display->framebuffer)
  {
    memcpy(display->mapped, display->output, display->output_size);
    msync(display->mapped, display->output_size, MS_SYNC);
    ioctl(display->fd, FBIO_BLIT, 0);
    return true;
  }
  if (lseek(display->fd, 0, SEEK_SET) < 0 && errno != ESPIPE)
    return false;
  return write_all(display->fd, display->output, display->output_size);
}

static const char *first_text_widget(void)
{
  int index;
  for (index = 0; index < MAX_WIDGETS; ++index)
    if (widgets[index].type == WIDGET_SCROLLER && widgets[index].text[0])
      return widgets[index].text;
  return "Kodi";
}

static bool parse_time_value(const char *start, const char *limit, int *seconds)
{
  const char *cursor;
  for (cursor = start; cursor < limit; ++cursor)
  {
    char *end;
    long first;
    long second;
    long third = -1;
    if (*cursor < '0' || *cursor > '9')
      continue;
    first = strtol(cursor, &end, 10);
    if (end >= limit || *end != ':')
      continue;
    cursor = end + 1;
    if (cursor >= limit || *cursor < '0' || *cursor > '9')
      continue;
    second = strtol(cursor, &end, 10);
    if (second < 0 || second > 59)
      continue;
    if (end < limit && *end == ':')
    {
      cursor = end + 1;
      if (cursor >= limit || *cursor < '0' || *cursor > '9')
        continue;
      third = strtol(cursor, &end, 10);
      if (third < 0 || third > 59)
        continue;
    }
    *seconds = third >= 0
                   ? (int)(first * 3600 + second * 60 + third)
                   : (int)(first * 60 + second);
    return true;
  }
  return false;
}

static bool player_remaining_seconds(int *remaining)
{
  int index;
  for (index = 0; index < MAX_WIDGETS; ++index)
  {
    const struct widget *widget = &widgets[index];
    const char *separator;
    int elapsed;
    int duration;
    if (widget->type != WIDGET_SCROLLER || !widget->text[0])
      continue;
    separator = strchr(widget->text, '/');
    if (!separator ||
        !parse_time_value(widget->text, separator, &elapsed) ||
        !parse_time_value(separator + 1, widget->text + strlen(widget->text), &duration) ||
        duration <= 0)
      continue;
    *remaining = duration > elapsed ? duration - elapsed : 0;
    return true;
  }
  return false;
}

static void format_segment_time(char output[6], int seconds)
{
  int left;
  int right;
  if (seconds >= 3600)
  {
    left = seconds / 3600;
    right = (seconds / 60) % 60;
  }
  else
  {
    left = seconds / 60;
    right = seconds % 60;
  }
  if (left > 99)
    left = 99;
  snprintf(output, 6, "%02d:%02d", left, right);
}

static void format_segment_clock(char output[6])
{
  time_t now = time(NULL);
  struct tm local;
  if (localtime_r(&now, &local))
    snprintf(output, 6, "%02d:%02d", local.tm_hour, local.tm_min);
  else
    memcpy(output, "00:00", 6);
}

static bool flush_text(struct display *display)
{
  char output[256];
  const char *text = first_text_widget();
  size_t length;
  memset(output, ' ', sizeof(output));
  if (display->seven_segment)
  {
    int remaining;
    char segment_time[6];
    (void)text;
    /* Enigma2's final front-panel cleanup can race our initial setup.  Assert
     * the active 4x7 output directly before every clock/remaining-time write. */
    enable_seven_segment_panel();
    if (player_remaining_seconds(&remaining))
      format_segment_time(segment_time, remaining);
    else
      format_segment_clock(segment_time);

    /* Four digits plus the dedicated colon segment are written as HH:MM or
     * MM:SS.  The colon is not an LCDproc character cell on these panels. */
    return write_all(display->fd, (const uint8_t *)segment_time, 5);
  }
  else
  {
    length = strlen(text);
    if (length > (size_t)(display->columns * display->rows))
      length = (size_t)(display->columns * display->rows);
    memcpy(output, text, length);
  }
  length = (size_t)(display->columns * display->rows);
  return write_all(display->fd, (const uint8_t *)output, length);
}

static void render_display(struct display *display)
{
  bool result;
  if (display->graphical)
  {
    draw_widgets(display);
    result = flush_graphical(display);
  }
  else
  {
    result = flush_text(display);
  }
  if (!result)
    fprintf(stderr, "stb-lcdd: display update failed: %s\n", strerror(errno));
}

static struct widget *find_widget(const char *name, bool create)
{
  int index;
  struct widget *empty = NULL;
  for (index = 0; index < MAX_WIDGETS; ++index)
  {
    if (widgets[index].type != WIDGET_NONE && !strcmp(widgets[index].name, name))
      return &widgets[index];
    if (!empty && widgets[index].type == WIDGET_NONE)
      empty = &widgets[index];
  }
  if (create && empty)
  {
    memset(empty, 0, sizeof(*empty));
    snprintf(empty->name, sizeof(empty->name), "%s", name);
    return empty;
  }
  return NULL;
}

static int tokenize(char *line, char **arguments, int maximum)
{
  int count = 0;
  char *read = line;
  while (*read && count < maximum)
  {
    char *write;
    while (*read == ' ' || *read == '\t')
      ++read;
    if (!*read)
      break;
    if (*read == '"')
    {
      ++read;
      arguments[count++] = read;
      write = read;
      while (*read && *read != '"')
      {
        if (*read == '\\' && read[1])
          ++read;
        *write++ = *read++;
      }
      *write = '\0';
      if (*read == '"')
        ++read;
    }
    else
    {
      arguments[count++] = read;
      while (*read && *read != ' ' && *read != '\t')
        ++read;
      if (*read)
        *read++ = '\0';
    }
  }
  return count;
}

static bool send_reply(int client, const char *reply)
{
  size_t length = strlen(reply);
#ifdef MSG_NOSIGNAL
  return send(client, reply, length, MSG_NOSIGNAL) == (ssize_t)length;
#else
  return send(client, reply, length, 0) == (ssize_t)length;
#endif
}

static bool process_command(int client, char *line, const struct display *display, bool *dirty)
{
  char *arguments[MAX_ARGS];
  int count = tokenize(line, arguments, MAX_ARGS);
  struct widget *widget;
  if (count == 0)
    return true;

  if (!strcmp(arguments[0], "hello"))
  {
    char reply[256];
    snprintf(reply, sizeof(reply),
             "connect STB-LCDd 1.0 protocol 0.3 lcd wid %d hgt %d cellwid %d cellhgt %d\n",
             display->columns, display->rows, display->cell_width, display->cell_height);
    return send_reply(client, reply);
  }
  if (!strcmp(arguments[0], "info"))
    return send_reply(client, "STB frontpanel\n");
  if (!strcmp(arguments[0], "noop"))
    return send_reply(client, "noop complete\n");
  if (!strcmp(arguments[0], "bye"))
    return false;

  if (!strcmp(arguments[0], "screen_add"))
  {
    screen_visible = true;
    *dirty = true;
  }
  else if (!strcmp(arguments[0], "screen_set"))
  {
    int index;
    for (index = 2; index + 1 < count; ++index)
    {
      if (!strcmp(arguments[index], "-priority"))
        screen_visible = strcmp(arguments[index + 1], "hidden") != 0;
      if (!strcmp(arguments[index], "-backlight"))
        screen_visible = strcmp(arguments[index + 1], "off") != 0;
    }
    *dirty = true;
  }
  else if (!strcmp(arguments[0], "widget_add") && count >= 4)
  {
    widget = find_widget(arguments[2], true);
    if (widget)
    {
      if (!strcmp(arguments[3], "scroller"))
        widget->type = WIDGET_SCROLLER;
      else if (!strcmp(arguments[3], "hbar"))
        widget->type = WIDGET_HBAR;
      else if (!strcmp(arguments[3], "icon"))
        widget->type = WIDGET_ICON;
      else if (!strcmp(arguments[3], "num"))
        widget->type = WIDGET_NUM;
    }
  }
  else if (!strcmp(arguments[0], "widget_del") && count >= 3)
  {
    widget = find_widget(arguments[2], false);
    if (widget)
      memset(widget, 0, sizeof(*widget));
    *dirty = true;
  }
  else if (!strcmp(arguments[0], "widget_set") && count >= 3)
  {
    widget = find_widget(arguments[2], false);
    if (widget && widget->type == WIDGET_SCROLLER && count >= 10)
    {
      char old_text[MAX_TEXT];
      snprintf(old_text, sizeof(old_text), "%s", widget->text);
      widget->x = atoi(arguments[3]);
      widget->y = atoi(arguments[4]);
      widget->end_x = atoi(arguments[5]);
      widget->end_y = atoi(arguments[6]);
      widget->scroll_mode = arguments[7][0];
      widget->scroll_speed = atoi(arguments[8]);
      snprintf(widget->text, sizeof(widget->text), "%s", arguments[9]);
      if (strcmp(old_text, widget->text))
        widget->scroll = 0;
    }
    else if (widget && widget->type == WIDGET_HBAR && count >= 6)
    {
      widget->x = atoi(arguments[3]);
      widget->y = atoi(arguments[4]);
      widget->width = atoi(arguments[5]);
    }
    else if (widget && widget->type == WIDGET_ICON && count >= 7)
    {
      widget->x = atoi(arguments[3]);
      widget->y = atoi(arguments[4]);
      snprintf(widget->icon, sizeof(widget->icon), "%s", arguments[5]);
    }
    else if (widget && widget->type == WIDGET_ICON && count >= 6)
    {
      widget->x = atoi(arguments[3]);
      widget->y = atoi(arguments[4]);
      snprintf(widget->icon, sizeof(widget->icon), "%s", arguments[5]);
    }
    else if (widget && widget->type == WIDGET_NUM && count >= 5)
    {
      widget->x = atoi(arguments[3]);
      widget->number = atoi(arguments[4]);
    }
    *dirty = true;
  }

  return send_reply(client, "success\n");
}

static bool advance_scrollers(void)
{
  int index;
  bool changed = false;
  for (index = 0; index < MAX_WIDGETS; ++index)
  {
    struct widget *widget = &widgets[index];
    if (widget->type == WIDGET_SCROLLER && widget->text[0] && widget->scroll_mode != 'n')
    {
      int available = widget->end_x >= widget->x ? widget->end_x - widget->x + 1 : 0;
      if (available > 0 && utf8_length(widget->text) > available)
      {
        ++widget->scroll;
        changed = true;
      }
    }
  }
  return changed;
}

static int create_server(void)
{
  int server = socket(AF_INET, SOCK_STREAM | SOCK_CLOEXEC, 0);
  struct sockaddr_in address;
  int enabled = 1;
  if (server < 0)
    return -1;
  setsockopt(server, SOL_SOCKET, SO_REUSEADDR, &enabled, sizeof(enabled));
  memset(&address, 0, sizeof(address));
  address.sin_family = AF_INET;
  address.sin_port = htons(LCDD_PORT);
  address.sin_addr.s_addr = htonl(INADDR_LOOPBACK);
  if (bind(server, (struct sockaddr *)&address, sizeof(address)) < 0 || listen(server, 2) < 0)
  {
    close(server);
    return -1;
  }
  return server;
}

static void serve_client(int client, struct display *display, struct display *secondary)
{
  char input[8192];
  size_t used = 0;
  bool connected = true;
  bool dirty = true;
  long long last_scroll = monotonic_ms();
  long long last_segment_refresh = last_scroll;
  memset(widgets, 0, sizeof(widgets));
  screen_visible = true;

  while (running && connected)
  {
    fd_set read_set;
    struct timeval timeout = {0, 100000};
    int selected;
    FD_ZERO(&read_set);
    FD_SET(client, &read_set);
    selected = select(client + 1, &read_set, NULL, NULL, &timeout);
    if (selected < 0)
    {
      if (errno == EINTR)
        continue;
      break;
    }
    if (selected > 0 && FD_ISSET(client, &read_set))
    {
      ssize_t received = recv(client, input + used, sizeof(input) - used - 1, 0);
      if (received <= 0)
        break;
      used += (size_t)received;
      input[used] = '\0';
      while (connected)
      {
        char *newline = memchr(input, '\n', used);
        size_t length;
        char command[MAX_LINE];
        if (!newline)
          break;
        length = (size_t)(newline - input);
        if (length >= sizeof(command))
          length = sizeof(command) - 1;
        memcpy(command, input, length);
        command[length] = '\0';
        if (length && command[length - 1] == '\r')
          command[length - 1] = '\0';
        memmove(input, newline + 1, used - (size_t)(newline + 1 - input));
        used -= (size_t)(newline + 1 - input);
        connected = process_command(client, command, display, &dirty);
      }
      if (used == sizeof(input) - 1)
        used = 0;
    }
    if (monotonic_ms() - last_scroll >= 300)
    {
      if (advance_scrollers())
        dirty = true;
      last_scroll = monotonic_ms();
    }
    /* Several 7-segment front-panel drivers discard the first write while
     * ownership changes from Enigma2 to Kodi.  They also have no internal
     * clock: refresh HH:MM (or the player's remaining time) periodically. */
    if ((display->seven_segment || (secondary && secondary->seven_segment)) &&
        monotonic_ms() - last_segment_refresh >= 1000)
    {
      dirty = true;
      last_segment_refresh = monotonic_ms();
    }
    if (dirty)
    {
      render_display(display);
      if (secondary)
        render_display(secondary);
      dirty = false;
    }
  }
}

int main(void)
{
  struct config config;
  struct config secondary_config;
  struct display display;
  struct display secondary_display;
  struct display *secondary = NULL;
  int server;
  struct sigaction action;

  memset(&action, 0, sizeof(action));
  action.sa_handler = signal_stop;
  sigemptyset(&action.sa_mask);
  sigaction(SIGTERM, &action, NULL);
  sigaction(SIGINT, &action, NULL);
  sigaction(SIGHUP, &action, NULL);
  signal(SIGPIPE, SIG_IGN);

  load_config(&config);
  if (!display_init(&display, &config))
    return EXIT_SUCCESS;
  make_secondary_config(&config, &secondary_config);
  if (strcmp(secondary_config.mode, "none"))
  {
    if (display_init(&secondary_display, &secondary_config))
      secondary = &secondary_display;
    else
      display_close(&secondary_display);
  }
  server = create_server();
  if (server < 0)
  {
    fprintf(stderr, "stb-lcdd: cannot listen on 127.0.0.1:%d: %s\n", LCDD_PORT, strerror(errno));
    if (secondary)
      display_close(secondary);
    display_close(&display);
    return EXIT_FAILURE;
  }

  while (running)
  {
    fd_set read_set;
    struct timeval timeout = {1, 0};
    int selected;
    FD_ZERO(&read_set);
    FD_SET(server, &read_set);
    selected = select(server + 1, &read_set, NULL, NULL, &timeout);
    if (selected < 0)
    {
      if (errno == EINTR)
        continue;
      break;
    }
    if (selected > 0 && FD_ISSET(server, &read_set))
    {
      int client = accept4(server, NULL, NULL, SOCK_CLOEXEC);
      if (client >= 0)
      {
        serve_client(client, &display, secondary);
        close(client);
      }
    }
  }

  close(server);
  if (secondary)
    display_close(secondary);
  display_close(&display);
  return EXIT_SUCCESS;
}
