DESCRIPTION = "blindscan..."
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

# RRECOMMENDS names each brand's own blindscan tool, so this is machine specific
PACKAGE_ARCH = "${MACHINE_ARCH}"
RRECOMMENDS:${PN} += "${@bb.utils.contains('MACHINE_FEATURES', 'blindscan-dvbs', d.getVar('VIRTUAL-RUNTIME_blindscan_dvbs') or '', '', d)}"

# Plugin-only upgrades also need the PnP helper on Quad 4K, Quad 4K Pro and UE 4K.
RDEPENDS:${PN}:append:gb7252 = " gbbs"
PR:gb7252 = "r1"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/Blindscan.git;protocol=https;branch=main"
