DESCRIPTION = "OpenGL ES v1.0 library and headers"

require MastaG-apps.inc

PR = "r0"

DEPENDS += " directfb"
CFLAGS_append = " -I${STAGING_INCDIR}/directfb"

PROVIDES = "virtual/libgles1 virtual/egl"

INSANE_SKIP_${PN} += "dev-so"
