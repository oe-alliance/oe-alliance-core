/* SPDX-License-Identifier: GPL-3.0-or-later
 * DM900/DM920 libvc5dream default-window context (not Mali or NXPL).
 * Enigma2 owns fb0 geometry. Query the accepted EGL size, never resize fb0
 * while the suspended GUI still has its framebuffer mapping alive.
 */
#include <stdlib.h>
#include "../../config.h"
#include "../common/egl_common.h"
#include "../../frontend/frontend_driver.h"
#include "../../verbosity.h"

typedef struct
{
   egl_ctx_data_t egl;
   unsigned width, height;
} dream_ctx_t;

static void dream_destroy(void *data)
{
   dream_ctx_t *dream = (dream_ctx_t *)data;
   if (!dream)
      return;
   egl_destroy(&dream->egl);
   free(dream);
   RARCH_LOG("[Dream VC5] Context cleanup complete.\n");
}

static void *dream_init(void *video_driver)
{
   EGLint count, major, minor;
   const char *stage = "EGL display/config initialization";
   static const EGLint config_attributes[] = {
      EGL_RENDERABLE_TYPE, EGL_OPENGL_ES2_BIT, EGL_SURFACE_TYPE, EGL_WINDOW_BIT,
      EGL_RED_SIZE, 8, EGL_GREEN_SIZE, 8, EGL_BLUE_SIZE, 8, EGL_ALPHA_SIZE, 8, EGL_NONE
   };
   static const EGLint context_attributes[] = {EGL_CONTEXT_CLIENT_VERSION, 2, EGL_NONE};
   dream_ctx_t *dream = (dream_ctx_t *)calloc(1, sizeof(*dream));
   if (!dream)
      return NULL;
   frontend_driver_install_signal_handler();
   dream->egl.use_hw_ctx = true;
   if (!egl_init_context(&dream->egl, EGL_NONE, EGL_DEFAULT_DISPLAY,
            &major, &minor, &count, config_attributes, NULL))
      goto error;
   stage = "GLES2 API binding";
   if (!egl_bind_api(EGL_OPENGL_ES_API))
      goto error;
   stage = "GLES2 context creation";
   if (!egl_create_context(&dream->egl, context_attributes))
      goto error;
   stage = "default fbdev window surface/current context";
   /* NULL is the valid default native window on libvc5dream, not an error. */
   if (!egl_create_surface(&dream->egl, NULL))
      goto error;
   stage = "default-window size query";
   egl_get_video_size(&dream->egl, &dream->width, &dream->height);
   if (!dream->width || !dream->height)
      goto error;
   /* The default framebuffer is presented directly; no extra scene FBO or
    * preserved-buffer contract like the Chromium NXPL workaround. */
   if (!eglSurfaceAttrib(dream->egl.dpy, dream->egl.surf,
            EGL_SWAP_BEHAVIOR, EGL_BUFFER_DESTROYED))
      RARCH_WARN("[Dream VC5] Could not select destroyed swap behavior.\n");
   RARCH_LOG("[Dream VC5] GLES2 default fbdev window ready: %ux%u.\n",
         dream->width, dream->height);
   return dream;
error:
   RARCH_ERR("[Dream VC5] Failed at %s.\n", stage);
   egl_report_error();
   dream_destroy(dream);
   return NULL;
}

static enum gfx_ctx_api dream_get_api(void *data) { return GFX_CTX_OPENGL_ES_API; }
static bool dream_bind_api(void *data, enum gfx_ctx_api api, unsigned major, unsigned minor)
{
   /* Capability probe only, without pre-init vendor calls. */
   return api == GFX_CTX_OPENGL_ES_API && major <= 2;
}
static void dream_swap_interval(void *data, int interval)
{
   egl_set_swap_interval(&((dream_ctx_t *)data)->egl, interval);
}
static bool dream_set_video_mode(void *data, unsigned width, unsigned height, bool fullscreen)
{
   return data != NULL;
}
static void dream_get_video_size(void *data, unsigned *width, unsigned *height)
{
   dream_ctx_t *dream = (dream_ctx_t *)data;
   *width = dream->width;
   *height = dream->height;
}
static float dream_refresh_rate(void *data) { return 60.0f; }
static void dream_check_window(void *data, bool *quit, bool *resize, unsigned *width, unsigned *height)
{
   *quit = (bool)frontend_driver_get_signal_handler_state();
   *resize = false;
}
static bool dream_has_focus(void *data) { return true; }
static bool dream_suppress_screensaver(void *data, bool enable) { return false; }
static void dream_swap_buffers(void *data) { egl_swap_buffers(&((dream_ctx_t *)data)->egl); }
static void dream_input_driver(void *data, const char *name, input_driver_t **input, void **input_data)
{
   *input = NULL;
   *input_data = NULL;
}
static uint32_t dream_get_flags(void *data)
{
   uint32_t flags = 0;
   BIT32_SET(flags, GFX_CTX_FLAGS_SHADERS_GLSL);
   return flags;
}
static void dream_set_flags(void *data, uint32_t flags) { }
static void dream_bind_hw_render(void *data, bool enable)
{
   egl_bind_hw_render(&((dream_ctx_t *)data)->egl, enable);
}
const gfx_ctx_driver_t gfx_ctx_mali_fbdev = {
   dream_init, dream_destroy, dream_get_api, dream_bind_api, dream_swap_interval,
   dream_set_video_mode, dream_get_video_size, dream_refresh_rate,
   NULL, NULL, NULL, NULL, NULL, NULL,
   dream_check_window, NULL, dream_has_focus, dream_suppress_screensaver, false,
   dream_swap_buffers, dream_input_driver, egl_get_proc_address,
   NULL, NULL, NULL, "dream_vc5", dream_get_flags, dream_set_flags,
   dream_bind_hw_render, NULL, NULL
};
