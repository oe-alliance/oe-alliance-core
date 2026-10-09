/* SPDX-License-Identifier: GPL-3.0-or-later
 * Edision V3D context. This ABI is not interchangeable with Broadcom NXPL.
 * Use the selected provider's header and retain its DSO until process exit.
 */
#include <dlfcn.h>
#include <stdlib.h>
#include <v3dplatform.h>

#include "../../config.h"
#include "../common/egl_common.h"
#include "../../frontend/frontend_driver.h"
#include "../../verbosity.h"

#define V3D_CANVAS_WIDTH 1280
#define V3D_CANVAS_HEIGHT 720

typedef struct
{
   egl_ctx_data_t egl;
   V3D_PlatformHandle platform;
   void *window;
   void (*unregister_display)(V3D_PlatformHandle);
   void (*destroy_window)(void *);
   bool (*set_visible)(void *, bool);
} v3d_ctx_t;

static void v3d_destroy(void *data)
{
   v3d_ctx_t *v3d = (v3d_ctx_t *)data;
   if (!v3d)
      return;
   egl_destroy(&v3d->egl);
   if (v3d->window && v3d->destroy_window)
      v3d->destroy_window(v3d->window);
   if (v3d->platform && v3d->unregister_display)
      v3d->unregister_display(v3d->platform);
   free(v3d);
   RARCH_LOG("[BCM V3D] Context cleanup complete.\n");
}

static bool v3d_create_window(v3d_ctx_t *v3d)
{
   void *library = dlopen("libv3dplatform.so", RTLD_LAZY | RTLD_GLOBAL);
   void (*register_display)(V3D_PlatformHandle *, void *);
   void *(*create_window)(const V3D_NativeWindowInfo *);
   V3D_NativeWindowInfo info = {0};
   if (!library)
   {
      RARCH_ERR("[BCM V3D] Cannot load Edision platform library: %s.\n", dlerror());
      return false;
   }
   register_display = (void (*)(V3D_PlatformHandle *, void *))dlsym(library, "V3D_RegisterDisplayPlatform");
   create_window = (void *(*)(const V3D_NativeWindowInfo *))dlsym(library, "V3D_CreateNativeWindow");
   v3d->unregister_display = (void (*)(V3D_PlatformHandle))dlsym(library, "V3D_UnregisterDisplayPlatform");
   v3d->destroy_window = (void (*)(void *))dlsym(library, "V3D_DestroyNativeWindow");
   v3d->set_visible = (bool (*)(void *, bool))dlsym(library, "V3D_SetVisible");
   if (!register_display || !create_window || !v3d->unregister_display ||
         !v3d->destroy_window || !v3d->set_visible)
   {
      RARCH_ERR("[BCM V3D] Required Edision lifecycle symbols missing.\n");
      return false;
   }
   register_display(&v3d->platform, EGL_DEFAULT_DISPLAY);
   if (!v3d->platform)
   {
      RARCH_ERR("[BCM V3D] Display registration failed.\n");
      return false;
   }
   info.width = V3D_CANVAS_WIDTH;
   info.height = V3D_CANVAS_HEIGHT;
   info.stretch = false;
   info.clientID = 0;
   info.zOrder = 0;
   v3d->window = create_window(&info);
   if (!v3d->window)
      RARCH_ERR("[BCM V3D] Native window creation failed.\n");
   return v3d->window != NULL;
}

static void *v3d_init(void *video_driver)
{
   EGLint count, major, minor;
   const char *stage = "EGL display/config initialization";
   static const EGLint config_attributes[] = {
      EGL_RENDERABLE_TYPE, EGL_OPENGL_ES2_BIT, EGL_SURFACE_TYPE, EGL_WINDOW_BIT,
      EGL_RED_SIZE, 8, EGL_GREEN_SIZE, 8, EGL_BLUE_SIZE, 8, EGL_ALPHA_SIZE, 8, EGL_NONE
   };
   static const EGLint context_attributes[] = {EGL_CONTEXT_CLIENT_VERSION, 2, EGL_NONE};
   v3d_ctx_t *v3d = (v3d_ctx_t *)calloc(1, sizeof(*v3d));
   if (!v3d)
      return NULL;
   frontend_driver_install_signal_handler();
   if (!v3d_create_window(v3d))
      goto error;
   v3d->egl.use_hw_ctx = true;
   if (!egl_init_context(&v3d->egl, EGL_NONE, EGL_DEFAULT_DISPLAY,
            &major, &minor, &count, config_attributes, NULL))
      goto egl_error;
   stage = "GLES2 API binding";
   if (!egl_bind_api(EGL_OPENGL_ES_API))
      goto egl_error;
   stage = "GLES2 context creation";
   if (!egl_create_context(&v3d->egl, context_attributes))
      goto egl_error;
   stage = "EGL window surface/current context";
   if (!egl_create_surface(&v3d->egl, v3d->window))
      goto egl_error;
   /* The vendor header and v2.0 implementation require the WINDOW here,
    * not the display-platform handle used by some older Kodi sources. */
   if (!v3d->set_visible(v3d->window, true))
   {
      RARCH_ERR("[BCM V3D] Cannot show native window.\n");
      goto error;
   }
   RARCH_LOG("[BCM V3D] Edision GLES2 native window ready: %ux%u.\n",
         V3D_CANVAS_WIDTH, V3D_CANVAS_HEIGHT);
   return v3d;
egl_error:
   RARCH_ERR("[BCM V3D] Failed at %s.\n", stage);
   egl_report_error();
error:
   v3d_destroy(v3d);
   return NULL;
}

static enum gfx_ctx_api v3d_get_api(void *data) { return GFX_CTX_OPENGL_ES_API; }
static bool v3d_bind_api(void *data, enum gfx_ctx_api api, unsigned major, unsigned minor)
{
   /* Capability probe only: vendor EGL must not be called before init. */
   return api == GFX_CTX_OPENGL_ES_API && major <= 2;
}
static void v3d_swap_interval(void *data, int interval)
{
   egl_set_swap_interval(&((v3d_ctx_t *)data)->egl, interval);
}
static bool v3d_set_video_mode(void *data, unsigned width, unsigned height, bool fullscreen)
{
   return data != NULL;
}
static void v3d_get_video_size(void *data, unsigned *width, unsigned *height)
{
   *width = V3D_CANVAS_WIDTH;
   *height = V3D_CANVAS_HEIGHT;
}
static float v3d_refresh_rate(void *data) { return 60.0f; }
static void v3d_check_window(void *data, bool *quit, bool *resize, unsigned *width, unsigned *height)
{
   *quit = (bool)frontend_driver_get_signal_handler_state();
   *resize = false;
}
static bool v3d_has_focus(void *data) { return true; }
static bool v3d_suppress_screensaver(void *data, bool enable) { return false; }
static void v3d_swap_buffers(void *data) { egl_swap_buffers(&((v3d_ctx_t *)data)->egl); }
static void v3d_input_driver(void *data, const char *name, input_driver_t **input, void **input_data)
{
   *input = NULL;
   *input_data = NULL;
}
static uint32_t v3d_get_flags(void *data)
{
   uint32_t flags = 0;
   BIT32_SET(flags, GFX_CTX_FLAGS_SHADERS_GLSL);
   return flags;
}
static void v3d_set_flags(void *data, uint32_t flags) { }
static void v3d_bind_hw_render(void *data, bool enable)
{
   egl_bind_hw_render(&((v3d_ctx_t *)data)->egl, enable);
}
const gfx_ctx_driver_t gfx_ctx_mali_fbdev = {
   v3d_init, v3d_destroy, v3d_get_api, v3d_bind_api, v3d_swap_interval,
   v3d_set_video_mode, v3d_get_video_size, v3d_refresh_rate,
   NULL, NULL, NULL, NULL, NULL, NULL,
   v3d_check_window, NULL, v3d_has_focus, v3d_suppress_screensaver, false,
   v3d_swap_buffers, v3d_input_driver, egl_get_proc_address,
   NULL, NULL, NULL, "bcm_v3d", v3d_get_flags, v3d_set_flags,
   v3d_bind_hw_render, NULL, NULL
};
