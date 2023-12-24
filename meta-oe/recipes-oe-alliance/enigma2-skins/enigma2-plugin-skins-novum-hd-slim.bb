SUMMARY = "NOVUM_HD_Slim by Nashu"
MAINTAINER = "rossi2000"
inherit allarch

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "19.2+git"
PKGV = "19.2+git${GITPKGV}"
PR = "r0"

RDEPENDS:${PN} = "enigma2-plugin-skincomponents-novum enigma2-plugin-systemplugins-weathercomponenthandler"

SRC_URI = "git://github.com/oe-alliance/oe-alliance-skins.git;protocol=https;branch=master"

FILES:${PN} = "/usr/share/enigma2/NOVUM_HD_Slim"

S = "${WORKDIR}/git"

do_install() {
    install -d ${D}/usr/share/enigma2
    cp -rp ${S}/Nashu/NOVUM_HD_Slim ${D}/usr/share/enigma2/
    chmod -R a+rX ${D}/usr/share/enigma2/
}

do_package_qa[noexec] = "1"
