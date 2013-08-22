DESCRIPTION = "MatrixHD skins for Enigma2"
MAINTAINER = "Steve Wheeler"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

SRCREV = "${AUTOREV}"
PV = "2.0+git${SRCPV}"
PKGV = "2.0+git${GITPKGV}"
PR = "r19"

PACKAGES += "matrixhd-components"
PROVIDES += "matrixhd-components"

SRC_URI="git://github.com/scwheeler/MetrixHD-for_VIX.git;protocol=git"

FILES_${PN}-dbg = "/usr/share/backdrop.mvi /usr/share/bootlogo.mvi"
FILES_matrixhd-components = " \
	/usr/lib/enigma2/python/Plugins \
	/usr/lib/enigma2/python/Components/Converter \
	/usr/lib/enigma2/python/Components/Renderer/MovieReference.py \
	/usr/lib/enigma2/python/Components/Renderer/NextEvent.py \
	/usr/lib/enigma2/python/Components/Renderer/VReference.py \
	/usr/lib/enigma2/python/Components/Renderer/VVolumeText.py \
	/usr/lib/enigma2/python/Components/Renderer/XPicon.py \
	/usr/lib/enigma2/python/Components/Renderer/XPiconChannel.py \
	"
FILES_${PN} = "/usr/share/enigma2/MetrixHD"

RDEPENDS_${PN} = "matrixhd-components"

S = "${WORKDIR}/git"

do_install() {
	install -d ${D}/usr/
	cp -rp ${S}/usr/* ${D}/usr/
	install -d ${D}/tmp
	chmod -R a+rX ${D}/usr/
}
