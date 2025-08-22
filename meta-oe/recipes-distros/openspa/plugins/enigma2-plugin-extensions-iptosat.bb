SUMMARY = "IPtoSAT by zkhadiri - MOD norhap Manager IPTV"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"
require conf/python/python3-compileall.inc

CONFFILES = "${sysconfdir}/enigma2"

inherit gitpkgv allarch

SRCREV = "${AUTOREV}"
PV = "2.1+git"
PKGV = "2.1+git${GITPKGV}"

SRC_URI = "git://github.com/norhap/IPtoSAT.git;protocol=https;branch=main"

S = "${WORKDIR}/git"

RDEPENDS:${PN} = "\
	exteplayer3 \
	gstplayer \
	"

FILES:${PN} = "${sysconfdir} ${libdir}"

do_install() {
    install -d ${D}${sysconfdir}/enigma2
    install -d ${D}${libdir}/enigma2/python/Plugins/Extensions/IPToSAT
    cp -rf ${S}/src/etc/enigma2/* ${D}${sysconfdir}/enigma2/
    cp -rf ${S}/src/IPtoSAT/* ${D}${libdir}/enigma2/python/Plugins/Extensions/IPToSAT/
}
