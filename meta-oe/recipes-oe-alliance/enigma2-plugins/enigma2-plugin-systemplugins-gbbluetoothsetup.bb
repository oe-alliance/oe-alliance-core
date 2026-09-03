DESCRIPTION = "GigaBlue bluetooth plugin"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS:${PN} = "gb-bluetooth-util-${MACHINE}"

COMPATIBLE_MACHINE = "^(gb7252|gb72604)$"

PN = "enigma2-plugin-systemplugins-bluetoothsetup"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/GigaBlueBluetoothSetup.git;protocol=https;branch=main"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INSANE_SKIP:${PN} += "ldflags"
INSANE_SKIP = "32bit-time"
