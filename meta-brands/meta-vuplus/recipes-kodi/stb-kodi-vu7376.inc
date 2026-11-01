PROVIDES += "virtual/kodi"
RPROVIDES:${PN} += "virtual-kodi"
PROVIDES += "kodi"
RPROVIDES:${PN} += "kodi"

INSANE_SKIP:${PN} += "file-rdeps"

# Build against a common 17.1 GLES blob instead of the per-model virtual/egl; NXPL is loaded at runtime.
VUPLUS_GLES_LINK = "${STAGING_LIBDIR}/vuplus-libgles-link"
DEPENDS:remove = "virtual/egl"
DEPENDS += "vuplus-libgles-link"
ASSUME_SHLIBS:append = " libv3ddriver.so:virtual-egl"
CFLAGS:prepend = "-I${VUPLUS_GLES_LINK}/include "
CXXFLAGS:prepend = "-I${VUPLUS_GLES_LINK}/include "
LDFLAGS:prepend = "-L${VUPLUS_GLES_LINK} "

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=vuplus-cortexa15 \
    -DEGL_INCLUDE_DIR=${VUPLUS_GLES_LINK}/include \
    -DEGL_LIBRARY=${VUPLUS_GLES_LINK}/libEGL.so \
    -DOPENGLES_INCLUDE_DIR=${VUPLUS_GLES_LINK}/include \
    -DOPENGLES3_INCLUDE_DIR=${VUPLUS_GLES_LINK}/include \
    -DOPENGLES_gl_LIBRARY=${VUPLUS_GLES_LINK}/libGLESv2.so \
"
