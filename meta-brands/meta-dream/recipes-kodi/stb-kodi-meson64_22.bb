require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

SUMMARY:append = " for Dreambox Amlogic"

PROVIDES += "virtual/kodi kodi"
RPROVIDES:${PN} += "virtual-kodi kodi"

COMPATIBLE_MACHINE = "^(dreamone|dreamtwo)$"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=amlogic-meson64 \
"

# Dream's Amlogic 4.9 amstream driver has no GET_VDEC_INFO ioctl.  Its
# libamcodec wrapper retries the unsupported ioctl indefinitely instead of
# returning ENOTTY, so Kodi must not call that optional API on DreamOne/Two.
CXXFLAGS:append = " -DSTB_AMLOGIC_NO_VDEC_INFO -DSTB_AMLOGIC_LEGACY_AMSTREAM"
