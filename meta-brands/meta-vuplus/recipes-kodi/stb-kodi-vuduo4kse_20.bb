require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

DEPENDS += "kodiegl"
RDEPENDS_${PN} += "kodiegl"

SRC_URI_append = " file://vuplus-fix-exception-duo4k-19.patch"

PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"

INSANE_SKIP_${PN} += "file-rdeps"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=vuplus-cortexa15 \
    -DWITH_FFMPEG=stb \
"

