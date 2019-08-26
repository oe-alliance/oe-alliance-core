SUMMARY = "PLi HD FullNight skin"
MAINTAINER = "littlesat"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file:/${datadir}/enigma2/PLi-HD-FullNight/skin.xml;beginline=3;endline=8;md5=b116f43110ecf49187a08e984a564a70"
inherit allarch

inherit gitpkgv

PV = "0.1+git${SRCPV}"
PKGV = "0.1+git${GITPKGV}"
PR = "r0"

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/littlesat/PLi-HD-FullNight.git"

FILES_${PN} = "${datadir}/enigma2/"

S = "${WORKDIR}/git"

do_compile() {
}

do_install() {
	install -d ${D}${datadir}
	cp -rp ${S}${datadir}/* ${D}${datadir}/
	chmod -R a+rX ${D}${datadir}/enigma2/
}

do_package_qa[noexec] = "1"
