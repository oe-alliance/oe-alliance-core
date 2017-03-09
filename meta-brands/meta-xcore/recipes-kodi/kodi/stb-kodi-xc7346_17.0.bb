require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append += " \
    file://0001-Krypton-V3D-support.patch \
"

EXTRA_OECONF += " \
	--with-platform=v3d \
	"