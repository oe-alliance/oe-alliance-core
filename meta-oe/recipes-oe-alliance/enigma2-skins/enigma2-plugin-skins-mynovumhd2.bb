SUMMARY = "myNOVUM_HD2 by Nashu"
MAINTAINER = "rossi2000"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

SRCREV = "${AUTOREV}"
PV = "3.4+git${SRCPV}"
PKGV = "3.4+git${GITPKGV}"
PR = "r1"

SRC_URI = "git://github.com/oe-alliance/oe-alliance-skins.git;protocol=git"

FILES_${PN} = "/usr/share/enigma2/myNOVUM_HD2"

S = "${WORKDIR}/git"

do_compile_append() {
   python -O -m compileall ${S}
}

do_install() {
    install -d ${D}/usr/share/enigma2
    mv ${S}/Nashu/myNOVUM_HD2 ${D}/usr/share/enigma2/
    chmod -R a+rX ${D}/usr/share/enigma2/
}
