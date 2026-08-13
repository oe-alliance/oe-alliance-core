require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
RPROVIDES:${PN} += "virtual-kodi"
PROVIDES += "kodi"
RPROVIDES:${PN} += "kodi"

INSANE_SKIP:${PN} += "file-rdeps"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=vuplus-cortexa15 \
"

# Kodi already requests and builds for GLES2, but this V3D driver reports a
# GLES 3.1 runtime version and makes Kodi select its GLES3 renderer path.
# Keep Kodi on its GLES2 shaders and texture handling for this machine.
SRC_URI += "file://0047-vuzero4k-force-runtime-gles2.patch"
CXXFLAGS:append = " -DTARGET_VUZERO4K"
PR:append = ".2"
