require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
RPROVIDES:${PN} += "virtual-kodi"
PROVIDES += "kodi"
RPROVIDES:${PN} += "kodi"
RDEPENDS:${PN} += "xtrend-v3ddriver-et10000"

# The legacy BCM7425 V3D userspace is GLES2-only even when the build
# sysroot also provides GLES3 headers.  Keep Kodi out of the GLES3 capture
# path, which requires glBlitFramebuffer from the vendor binary.
PR:append = ".1"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=v3d-mipsel \
    -DOPENGLES_FORCE_GLES2=ON \
"
