DESCRIPTION = "MatrixHD skins for Enigma2"
MAINTAINER = "Steve Wheeler"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

EPSM = "enigma2-plugin-skins"
SRCREV = "${AUTOREV}"
PV = "2.0+git${SRCPV}"
PKGV = "2.0+git${GITPKGV}"
PR = "r15"

PACKAGES += "matrixhd-components"
PROVIDES += "matrixhd-components"

SRC_URI="git://github.com/scwheeler/MetrixHD-for_VIX.git;protocol=git"

FILES_matrixhd-components = " \
	/usr/lib/enigma2/python/Plugins \
	/usr/lib/enigma2/python/Components \
	/usr/lib/enigma2/python/Renderer/MovieReference.py \
	/usr/lib/enigma2/python/Renderer/NextEvent.py \
	/usr/lib/enigma2/python/Renderer/VReference.py \
	/usr/lib/enigma2/python/Renderer/VVolumeText.py \
	/usr/lib/enigma2/python/Renderer/XPicon.py \
	/usr/lib/enigma2/python/Renderer/XPiconChannel.py \
	/temp/RunningText.py \
	"
FILES_${EPSM}-matrixhd = "/usr/share/enigma2/MetrixHD"

RDEPENDS_${EPSM}-matrixhd = "matrixhd-components"

S = "${WORKDIR}/git"

do_install() {
	install -d ${D}/usr/
	cp -rp ${S}/usr/* ${D}/usr/
	cp -p ${S}/usr/lib/enigma2/python/Components/Renderer/RunningText.py ${D}/temp/RunningText.py
	chmod -R a+rX ${D}/usr/
}

pkg_preinst_matrixhd-components() {
#!/bin/sh
if [ ! -f /usr/lib/enigma2/python/Components/Renderer/RunningText.pyo ];then
	cp -rp /temp/RunningText.py /usr/lib/enigma2/python/Components/Renderer/RunningText.py
fi
}
