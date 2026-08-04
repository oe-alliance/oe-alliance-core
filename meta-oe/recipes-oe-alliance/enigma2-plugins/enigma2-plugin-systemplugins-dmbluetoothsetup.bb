DESCRIPTION = "Dreambox bluetooth plugin"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "^(dreamone|dreamtwo)$"

PN = "enigma2-plugin-systemplugins-bluetoothsetup"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/DMBluetoothSetup.git;protocol=https;branch=main"

FILES:${PN} += "/usr/lib/enigma2/python/Plugins/SystemPlugins/BluetoothSetup/"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INSANE_SKIP:${PN} += "32bit-time ldflags"
