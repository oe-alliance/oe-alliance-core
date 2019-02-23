require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"
RDEPENDS_${PN} += "v3d-libgles-${MACHINE}"

SRC_URI_append = " \
	file://not_implementation_pcm_alsa.patch \
"

EXTRA_OECONF += " \
    --with-platform=dags-cortexa15 \
    --with-ffmpeg=stb \
"

INSANE_SKIP_${PN} += "file-rdeps"
