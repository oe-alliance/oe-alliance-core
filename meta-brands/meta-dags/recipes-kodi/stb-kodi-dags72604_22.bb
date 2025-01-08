require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
RPROVIDES:${PN} += "virtual-kodi"
PROVIDES += "kodi"
RPROVIDES:${PN} += "kodi"
RDEPENDS:${PN} += "v3d-libgles-${MACHINE}"

SRC_URI:append = " \
	file://not_implementation_pcm_alsa20.patch \
"

EXTRA_OECMAKE += " \
    -DWITH_PLATFORM=dags-cortexa15 \
    -DWITH_FFMPEG=stb \
"

INSANE_SKIP:${PN} += "file-rdeps"
