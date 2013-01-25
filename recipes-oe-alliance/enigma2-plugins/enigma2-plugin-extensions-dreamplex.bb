DESCRIPTION = "Plex Client for Enigma2 by DonDavici"
MAINTAINER = "oe-alliance"

require conf/license/license-gplv2.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r1"

SRC_URI = "git://github.com/DonDavici/DreamPlex.git;protocol=git"

S = "${WORKDIR}/git"

RDEPENDS_${PN} = "gst-plugins-bad-fragmented curl"

PLUGIN = "DreamPlex"

FILES_${PN} = "/usr/lib/enigma2/python/Plugins/Extensions/${PLUGIN}"

do_install() {
	mkdir -p ${D}/usr/lib/enigma2/python/Plugins/Extensions/${PLUGIN}
	cp -rp ${S}/src/* ${D}/usr/lib/enigma2/python/Plugins/Extensions/${PLUGIN}
}
