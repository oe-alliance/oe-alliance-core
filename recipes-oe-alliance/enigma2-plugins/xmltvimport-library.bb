DESCRIPTION = "Imports xmltv files into the EPG cache of enigma2"
MAINTAINER = "MiLo, rytec @ pli-images.org"

require conf/license/license-gplv2.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r28"

SRC_URI = "git://github.com/oe-alliance/XMTV-Import.git;protocol=git"

S = "${WORKDIR}/git/dreamcrc"

inherit distutils

DEPENDS = "python"
RDEPENDS = "python-compression python-shell"
PACKAGES = "${PN} ${PN}-dbg"

FILES_${PN} = "/usr/lib/enigma2/python/Plugins/Extensions/EPGImport/dreamcrc.so"
FILES_${PN}-dbg = "/usr/lib/enigma2/python/Plugins/Extensions/EPGImport/.debug /usr/src/debug"

do_install_append() {
	# silly hacky me, this could be done by distutils, but i can't figure it out...
	install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/EPGImport
	mv ${D}/usr/lib/python*/site-packages/*.so ${D}/usr/lib/enigma2/python/Plugins/Extensions/EPGImport
	rm -rf ${D}/usr/lib/python*/site-packages
	rm -rf ${D}/usr/lib/python2.7
}

# skip this!
install_egg_info() {
}
