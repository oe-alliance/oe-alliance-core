require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"
RDEPENDS_${PN} += "hd-v3ddriver-${MACHINE}"

EXTRA_OECONF += " \
    --with-platform=v3d-cortexa15 \
    --with-ffmpeg=v3d \
"

INSANE_SKIP_${PN} += "file-rdeps"
