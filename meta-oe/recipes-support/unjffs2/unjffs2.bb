SUMMARY = "unjffs2 is a very simple jffs2 unpacker"
LICENSE = "GPL"

LIC_FILES_CHKSUM = "file://unjffs2.cpp;beginline=19;endline=19;md5=bde85ed4c68e51358abcb6073c25cbdc"

inherit gitpkgv

PKGV = "1.0+git${GITPKGV}"
PV = "1.0+git${SRCPV}"
SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/athoik/unjffs2.git;protocol=https"

S = "${WORKDIR}/git"

do_compile() {
	oe_runmake
}

do_install() {
	install -d ${D}${bindir}
	install unjffs2 ${D}${bindir}/
}

