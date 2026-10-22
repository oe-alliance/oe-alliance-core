DESCRIPTION = "Dreambox bluetooth plugin"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "^(dm7080|dm900|dm920|dms922x)$"

PN = "enigma2-plugin-systemplugins-bluetoothsetup"

RDEPENDS:${PN}:append:dm900 = " flash-nrf52"
RDEPENDS:${PN}:append:dm920 = " flash-nrf52"
RDEPENDS:${PN}:append:dreamone = " nrf52-firmware-rcu"
RDEPENDS:${PN}:append:dreamtwo = " nrf52-firmware-rcu"

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
INSANE_SKIP:${PN} += "ldflags"
INSANE_SKIP = "32bit-time"
