require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"
RDEPENDS_${PN} += "v3d-libgles-${MACHINE}"

SRC_URI_append = " \
	file://not_implementation_pcm_alsa19.patch \
"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=dags-cortexa15 \
    -DWITH_FFMPEG=stb \
"

INSANE_SKIP_${PN} += "file-rdeps"
