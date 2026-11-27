require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

SUMMARY:append = " for GTMedia GTX Combo"
COMPATIBLE_MACHINE = "^gtxcombo$"
PROVIDES += "virtual/kodi kodi"
RPROVIDES:${PN} += "virtual-kodi kodi"

DREAM_AMLOGIC_STB = "1"
EXTRA_OECMAKE += "-DWITH_PLATFORM=amlogic-meson64"

PR:append = ".1"
SRC_URI:append:gtxcombo = " file://0001-gtx-kodi-playback.patch"
