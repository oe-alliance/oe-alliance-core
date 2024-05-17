require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

DEPENDS += "kodiegl"
RDEPENDS:${PN} += "kodiegl"

SRC_URI:append = " file://vuplus-fix-exception-duo4k-21.patch"

PROVIDES += "virtual/kodi"
RPROVIDES:${PN} += "virtual-kodi"
PROVIDES += "kodi"
RPROVIDES:${PN} += "kodi"

INSANE_SKIP:${PN} += "file-rdeps"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=vuplus-cortexa15 \
    -DWITH_FFMPEG=stb \
"

