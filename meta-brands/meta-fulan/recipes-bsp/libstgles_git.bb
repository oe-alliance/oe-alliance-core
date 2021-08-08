DESCRIPTION = "OpenGL ES v1.0 library and headers"

require ddt-apps.inc

PR = "r0"

DEPENDS += " directfb"
CFLAGS:append = " -I${STAGING_INCDIR}/directfb -I${WORKDIR}/git/libs/libstgles/includes"

PROVIDES = "virtual/libgles1 virtual/egl"

S = "${WORKDIR}/git/libs/libstgles"

INSANE_SKIP:${PN} += "dev-so"
