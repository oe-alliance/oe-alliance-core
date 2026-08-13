require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
RPROVIDES:${PN} += "virtual-kodi"
PROVIDES += "kodi"
RPROVIDES:${PN} += "kodi"
RDEPENDS:${PN} += "skylake-v3ddriver-${MACHINE}"

# The 20170322 BCM7425 V3D binary only implements GLES2.  Mesa's GLES3
# headers are nevertheless present in the sysroot, which otherwise makes
# Kodi reference glBlitFramebuffer at link time.
PR:append = ".1"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=v3d-mipsel \
    -DOPENGLES_FORCE_GLES2=ON \
"
