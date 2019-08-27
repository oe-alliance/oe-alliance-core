SUMMARY = "myNOVUM_HD2_Black by Nashu"
MAINTAINER = "rossi2000"
inherit allarch

require conf/license/license-gplv2.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "19.2+git${SRCPV}"
PKGV = "19.2+git${GITPKGV}"
PR = "r0"

RDEPENDS_${PN} = "enigma2-plugin-skincomponents-novum enigma2-plugin-systemplugins-weathercomponenthandler"

SRC_URI = "git://github.com/oe-alliance/oe-alliance-skins.git;protocol=git"

FILES_${PN} = "${datadir}/enigma2/myNOVUM_HD2_Black"

S = "${WORKDIR}/git"

do_compile_append() {
   python -O -m compileall ${S}
}

do_install() {
    install -d ${D}${datadir}/enigma2
    cp -rp ${S}/Nashu/myNOVUM_HD2_Black ${D}${datadir}/enigma2/
    chmod -R a+rX ${D}${datadir}/enigma2/
}

do_package_qa[noexec] = "1"
