DESCRIPTION = "Plugin to backup and restore EPG Data, including integration of EPGRefresh-plugin"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "enigma2-plugin-systemplugins-mphelp"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/EPGBackup.git;protocol=https;branch=main"

do_install:append() {
    install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/EPGBackup
    install -m 0755 ${S}/EPGBackup/EPGBackup.sh ${D}/usr/lib/enigma2/python/Plugins/Extensions/EPGBackup/EPGBackup.sh
}

FILES:${PN} += "/usr/lib/enigma2/python/Plugins/Extensions/EPGBackup"
