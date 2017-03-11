require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append += "\
	file://0001-v3d-platform.patch\
	file://0002-opengles-windowing.patch \
	file://0003-gui-and-settings.patch \
	file://0004-e2player.patch \
	"

EXTRA_OECONF += " \
	--with-platform=v3d \
	--with-ffmpeg=v3d \
	"