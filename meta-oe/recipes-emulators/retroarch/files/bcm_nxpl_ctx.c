/* SPDX-License-Identifier: GPL-3.0-or-later
 * Broadcom ARM NXPL context for RetroArch.
 * Runtime ABI and machine-selected lifecycle follow STB-Kodi/Chromium.
 * Only enabled on explicitly selected machines; this is not a generic BCM ABI.
 */
#include <dlfcn.h>
#include <stdint.h>
#include <stdlib.h>
#include <string.h>

#include "../../config.h"
#include "../common/egl_common.h"
#include "../../frontend/frontend_driver.h"
#include "../../verbosity.h"

typedef struct
{
   int a, b;
   bool subtract_cd;
   int c, d;
   bool subtract_e;
   int e;
} bcm_blend_t;

typedef struct
{
   uint32_t width, height, x, y;
   bool stretch;
   uint32_t client_id, z_order;
   bcm_blend_t color_blend, alpha_blend;
   uint32_t magic;
} bcm_window_info_t;

typedef struct
{
   egl_ctx_data_t egl;
   void *platform;
   void *window;
   bool registered;
   bool joined_nxclient;
   void (*unregister_display)(void *);
   void (*destroy_window)(void *);
   void (*show_window)(void *, bool);
   void (*uninit)(void);
} bcm_ctx_t;

/* The vendor plane scales the 720p canvas to the HDMI output. Never change
 * /dev/fb0 geometry: it belongs to Enigma2 and can use a different skin size. */
#define BCM_CANVAS_WIDTH 1280
#define BCM_CANVAS_HEIGHT 720

static void bcm_destroy(void *data)
{
   bcm_ctx_t *bcm = (bcm_ctx_t *)data;
   if (!bcm)
      return;
   /* EGL must release the surface before its underlying NXPL window. */
   egl_destroy(&bcm->egl);
#ifndef HAVE_BCM_VUPLUS
   if (bcm->window && bcm->destroy_window)
      bcm->destroy_window(bcm->window);
#ifndef HAVE_BCM_AUTHENTICATED
   if (bcm->registered && bcm->unregister_display)
      bcm->unregister_display(bcm->platform);
   if (bcm->joined_nxclient && bcm->uninit)
      bcm->uninit();
#else
   /* Kodi's V3D/NXPL path destroys the window but leaves authenticated
    * Nexus/platform ownership to process exit; it is not NxClient_Join. */
   RARCH_LOG("[BCM NXPL] Authenticated profile: native window released.\n");
#endif
#else
   /* Classic Vu+ Nexus objects must survive EGL teardown. Match the tested
    * Kodi path: no NXPL destroy/unregister or NxClient_Uninit on this SDK. */
   RARCH_LOG("[BCM NXPL] Vu+: native objects retained until process exit.\n");
#endif
   free(bcm);
   RARCH_LOG("[BCM NXPL] Context cleanup complete.\n");
}

static bool bcm_create_window(bcm_ctx_t *bcm)
{
   static const char *libraries[] = {
      "libnexus.so", "libdvb_base.so", "libdvb_client.so",
      "libnxclient.so", "libnxpl.so", "libv3ddriver.so"
   };
   unsigned i;
   bool joined = false;
#ifndef HAVE_BCM_AUTHENTICATED
   unsigned (*nxclient_join)(const void *);
#endif
#if !defined(HAVE_BCM_NEXTV) && !defined(HAVE_BCM_DAGS)
   int (*authenticated_join)(const void *);
#endif
   void (*register_display)(void **, void *);
   void (*default_window)(void *);
   void *(*create_window)(const void *);
   bcm_window_info_t info;

   /* Deliberately retain library handles until process exit. Unloading these
    * vendor libraries after EGL cleanup is unsafe on several Nexus stacks. */
   for (i = 0; i < sizeof(libraries) / sizeof(libraries[0]); ++i)
      dlopen(libraries[i], RTLD_LAZY | RTLD_GLOBAL);

#ifndef HAVE_BCM_AUTHENTICATED
   nxclient_join = (unsigned (*)(const void *))dlsym(RTLD_DEFAULT, "NxClient_Join");
#endif
#if !defined(HAVE_BCM_NEXTV) && !defined(HAVE_BCM_DAGS)
   authenticated_join = (int (*)(const void *))dlsym(RTLD_DEFAULT, "NEXUS_Platform_AuthenticatedJoin");
#endif
   register_display = (void (*)(void **, void *))dlsym(RTLD_DEFAULT, "NXPL_RegisterNexusDisplayPlatform");
   default_window = (void (*)(void *))dlsym(RTLD_DEFAULT, "NXPL_GetDefaultNativeWindowInfoEXT");
   create_window = (void *(*)(const void *))dlsym(RTLD_DEFAULT, "NXPL_CreateNativeWindowEXT");
   bcm->unregister_display = (void (*)(void *))dlsym(RTLD_DEFAULT, "NXPL_UnregisterNexusDisplayPlatform");
   bcm->destroy_window = (void (*)(void *))dlsym(RTLD_DEFAULT, "NXPL_DestroyNativeWindow");
   bcm->show_window = (void (*)(void *, bool))dlsym(RTLD_DEFAULT, "NXPL_ShowNativeWindowEXT");
   bcm->uninit = (void (*)(void))dlsym(RTLD_DEFAULT, "NxClient_Uninit");

   if (!register_display || !default_window || !create_window)
   {
      RARCH_ERR("[BCM NXPL] Required native-window symbols missing.\n");
      return false;
   }
#if defined(HAVE_BCM_AUTHENTICATED)
   if (!authenticated_join || !bcm->destroy_window || !bcm->show_window)
   {
      RARCH_ERR("[BCM NXPL] Required AuthenticatedJoin lifecycle symbols missing.\n");
      return false;
   }
   RARCH_LOG("[BCM NXPL] Profile: AuthenticatedJoin, explicit show/window release.\n");
#elif defined(HAVE_BCM_NEXTV)
   if (!nxclient_join || !bcm->unregister_display || !bcm->destroy_window ||
         !bcm->show_window || !bcm->uninit)
   {
      RARCH_ERR("[BCM NXPL] Required NexTV lifecycle symbols missing.\n");
      return false;
   }
   RARCH_LOG("[BCM NXPL] Profile: NexTV NxClient, unstretched window, explicit show/release.\n");
#elif defined(HAVE_BCM_DAGS)
   if (!nxclient_join || !bcm->unregister_display || !bcm->destroy_window ||
         !bcm->show_window || !bcm->uninit)
   {
      RARCH_ERR("[BCM NXPL] Required DAGS lifecycle symbols missing.\n");
      return false;
   }
   RARCH_LOG("[BCM NXPL] Profile: DAGS NxClient, unstretched window, explicit show/release.\n");
#elif !defined(HAVE_BCM_VUPLUS)
   if (!bcm->unregister_display || !bcm->destroy_window || !bcm->show_window || !bcm->uninit)
   {
      RARCH_ERR("[BCM NXPL] Required GigaBlue lifecycle symbols missing.\n");
      return false;
   }
   RARCH_LOG("[BCM NXPL] Profile: GigaBlue explicit show/release.\n");
#else
   RARCH_LOG("[BCM NXPL] Profile: classic Vu+ process-lifetime native objects.\n");
#endif
#ifndef HAVE_BCM_AUTHENTICATED
   if (nxclient_join)
   {
      unsigned result = nxclient_join(NULL);
      joined = result == 0;
      bcm->joined_nxclient = joined;
      RARCH_LOG("[BCM NXPL] NxClient_Join: %u.\n", result);
   }
#endif
#if !defined(HAVE_BCM_NEXTV) && !defined(HAVE_BCM_DAGS)
   if (!joined && authenticated_join)
   {
      int result = authenticated_join(NULL);
      joined = result == 0;
      RARCH_LOG("[BCM NXPL] AuthenticatedJoin: %d.\n", result);
   }
#endif
   if (!joined)
   {
      RARCH_ERR("[BCM NXPL] Cannot join Nexus server.\n");
      return false;
   }
   register_display(&bcm->platform, NULL);
   bcm->registered = true;
   memset(&info, 0, sizeof(info));
   default_window(&info);
   info.width = BCM_CANVAS_WIDTH;
   info.height = BCM_CANVAS_HEIGHT;
   info.x = info.y = 0;
#if defined(HAVE_BCM_NEXTV) || defined(HAVE_BCM_DAGS)
   /* Match Kodi's NexTV/DAGS windows; the plugin selects 720p60. */
   info.stretch = false;
#else
   info.stretch = true;
#endif
   info.client_id = 0;
#ifdef HAVE_BCM_AUTHENTICATED
   /* Keep the opaque game surface above the DVB/background plane. */
   info.z_order = 1;
#else
   info.z_order = 0;
#endif
   bcm->window = create_window(&info);
   if (!bcm->window)
      RARCH_ERR("[BCM NXPL] Native window creation failed.\n");
   return bcm->window != NULL;
}

static void *bcm_init(void *video_driver)
{
   EGLint count, major, minor;
   const char *stage = "EGL display/config initialization";
   static const EGLint config_attributes[] = {
      EGL_RENDERABLE_TYPE, EGL_OPENGL_ES2_BIT,
      EGL_SURFACE_TYPE, EGL_WINDOW_BIT,
      EGL_RED_SIZE, 8, EGL_GREEN_SIZE, 8, EGL_BLUE_SIZE, 8,
      EGL_ALPHA_SIZE, 8, EGL_NONE
   };
   static const EGLint context_attributes[] = {EGL_CONTEXT_CLIENT_VERSION, 2, EGL_NONE};
   bcm_ctx_t *bcm = (bcm_ctx_t *)calloc(1, sizeof(*bcm));
   if (!bcm)
      return NULL;
   frontend_driver_install_signal_handler();
   if (!bcm_create_window(bcm))
      goto error;
   bcm->egl.use_hw_ctx = true;
   /* RetroArch probes bind_api before init. Broadcom's vendor EGL cannot
    * bind then: join Nexus, register NXPL and initialize the display first,
    * matching the Kodi lifecycle. Bind on this rendering thread afterwards. */
   if (!egl_init_context(&bcm->egl, EGL_NONE, EGL_DEFAULT_DISPLAY,
            &major, &minor, &count, config_attributes, NULL))
      goto egl_error;
   stage = "GLES2 API binding";
   if (!egl_bind_api(EGL_OPENGL_ES_API))
      goto egl_error;
   stage = "GLES2 context creation";
   if (!egl_create_context(&bcm->egl, context_attributes))
      goto egl_error;
   stage = "EGL window surface/current context";
   if (!egl_create_surface(&bcm->egl, bcm->window))
      goto egl_error;
#ifndef HAVE_BCM_VUPLUS
   bcm->show_window(bcm->window, true);
#endif
   RARCH_LOG("[BCM NXPL] GLES2 native window ready: %ux%u.\n",
         BCM_CANVAS_WIDTH, BCM_CANVAS_HEIGHT);
   return bcm;

egl_error:
   RARCH_ERR("[BCM NXPL] Failed at %s.\n", stage);
   egl_report_error();
error:
   bcm_destroy(bcm);
   return NULL;
}

static enum gfx_ctx_api bcm_get_api(void *data)
{
   return GFX_CTX_OPENGL_ES_API;
}

static bool bcm_bind_api(void *data, enum gfx_ctx_api api, unsigned major, unsigned minor)
{
   if (api != GFX_CTX_OPENGL_ES_API || major > 2)
   {
      RARCH_ERR("[BCM NXPL] Only GLES2 is enabled for this tested vendor path.\n");
      return false;
   }
   /* Capability probe only; no vendor EGL calls before bcm_init(). */
   return true;
}

static void bcm_swap_interval(void *data, int interval)
{
   bcm_ctx_t *bcm = (bcm_ctx_t *)data;
   egl_set_swap_interval(&bcm->egl, interval);
}

static bool bcm_set_video_mode(void *data, unsigned width, unsigned height, bool fullscreen)
{
   return data != NULL;
}

static void bcm_get_video_size(void *data, unsigned *width, unsigned *height)
{
   *width = BCM_CANVAS_WIDTH;
   *height = BCM_CANVAS_HEIGHT;
}

static float bcm_refresh_rate(void *data)
{
   /* The launcher switches to a 60 Hz HDMI mode before opening this context. */
   return 60.0f;
}

static void bcm_check_window(void *data, bool *quit, bool *resize, unsigned *width, unsigned *height)
{
   *quit = (bool)frontend_driver_get_signal_handler_state();
   *resize = false;
}

static bool bcm_has_focus(void *data) { return true; }
static bool bcm_suppress_screensaver(void *data, bool enable) { return false; }

static void bcm_swap_buffers(void *data)
{
   bcm_ctx_t *bcm = (bcm_ctx_t *)data;
   egl_swap_buffers(&bcm->egl);
}

static void bcm_input_driver(void *data, const char *name, input_driver_t **input, void **input_data)
{
   *input = NULL;
   *input_data = NULL;
}

static uint32_t bcm_get_flags(void *data)
{
   uint32_t flags = 0;
   BIT32_SET(flags, GFX_CTX_FLAGS_SHADERS_GLSL);
   return flags;
}

static void bcm_set_flags(void *data, uint32_t flags) { }

static void bcm_bind_hw_render(void *data, bool enable)
{
   bcm_ctx_t *bcm = (bcm_ctx_t *)data;
   egl_bind_hw_render(&bcm->egl, enable);
}

/* Use the existing fbdev build slot, but expose an unambiguous runtime name.
 * The recipe compiles either Mali or NXPL, never a mixture of native ABIs. */
const gfx_ctx_driver_t gfx_ctx_mali_fbdev = {
   bcm_init, bcm_destroy, bcm_get_api, bcm_bind_api, bcm_swap_interval,
   bcm_set_video_mode, bcm_get_video_size, bcm_refresh_rate,
   NULL, NULL, NULL, NULL, NULL, NULL,
   bcm_check_window, NULL, bcm_has_focus, bcm_suppress_screensaver, false,
   bcm_swap_buffers, bcm_input_driver, egl_get_proc_address,
   NULL, NULL, NULL, "bcm_nxpl", bcm_get_flags, bcm_set_flags,
   bcm_bind_hw_render, NULL, NULL
};
