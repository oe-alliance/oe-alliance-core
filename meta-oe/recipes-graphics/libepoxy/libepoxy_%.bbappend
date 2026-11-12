# HbbTV 2.0 experiment: wpewebkit links libepoxy for its EGL/GLES2 use. OpenViX
# opts out of the "opengl" distro feature (vendor EGL on every box), which makes
# features_check skip this recipe. The target build only needs "egl" (no x11 in
# DISTRO_FEATURES), which the vendor virtual/egl provider supplies.
REQUIRED_DISTRO_FEATURES = ""

# The default resolves to "x11 egl" here, and x11 needs virtual/libgl (Mesa GL),
# which OpenViX does not provide. GLES via the vendor EGL is all wpewebkit needs.
PACKAGECONFIG = "egl"

# The Dreambox EGL headers (libvc5dream-dev) default eglplatform.h to X11 types
# unless BROADCOM_PLATFORM is defined, which selects generic void* native types
# (and pulls in the begl_*/dreameglplatform headers the vendor ships).
CFLAGS:append = " -DBROADCOM_PLATFORM"
