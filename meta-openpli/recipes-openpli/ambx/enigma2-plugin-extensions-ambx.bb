DESCRIPTION = "Create nifty light effects with an amBX kit"
MAINTAINER = "MiLo @ pli-images.org"

require conf/license/openpli-gplv2.inc

inherit gitpkgv

PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r0"

SRC_URI = "git://openpli.git.sourceforge.net/gitroot/openpli/plugin-ambx;protocol=git"

FILES_${PN} = "/usr/bin /usr/lib/enigma2/python"

S = "${WORKDIR}/git/plugin"

DEPENDS = "ambx"
RDEPENDS_${PN} = "python-pyambx"
PACKAGES = "${PN}"

PLUGIN = "amBX"

inherit distutils

FILES_${PN} = "/usr/lib/enigma2"

do_install_append() {
	# silly hacky me, this could be done by distutils, but i can't figure it out...
	install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions
	mv ${D}/usr/lib/python*/site-packages/${PLUGIN} ${D}/usr/lib/enigma2/python/Plugins/Extensions/${PLUGIN}
	rm -rf ${D}/usr/lib/python*/site-packages
	install -m 644 ${S}/${PLUGIN}/plugin.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/${PLUGIN}
}

