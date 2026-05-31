SUMMARY = "Spa24HD by OpenSPA"
MAINTAINER = "OpenSPA"

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gitpkgv allarch

SRCREV = "${AUTOREV}"
PV = "1.0+git"
PKGV = "1.0+git${GITPKGV}"
PR = "r1"

SRC_URI = "git://github.com/OpenSPA/openspa-enigma2-skins.git;protocol=https;branch=master"

FILES:${PN} = "/usr/share/enigma2 /usr/lib/enigma2/python/Components"

do_install() {
   install -d ${D}/usr/share/enigma2
   cp -r ${S}/${FOLDER}/Spa24HD ${D}/usr/share/enigma2/
   chmod -R a+rX ${D}/usr/share/enigma2/
   install -d ${D}/usr/lib/enigma2/python/Components
   cp -r --no-preserve=ownership ${S}/${FOLDER}/Components/* ${D}/usr/lib/enigma2/python/Components
}

do_install:append() {
    chown -R root:root ${D}
}

do_package_qa[noexec] = "1"
