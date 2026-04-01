DESCRIPTION = "blindscan..."
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RRECOMMENDS:${PN} += "${@bb.utils.contains('MACHINE_FEATURES', 'blindscan-dvbs', d.getVar('VIRTUAL-RUNTIME_blindscan_dvbs') or '', '', d)}"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/Blindscan.git;protocol=https;branch=main"
