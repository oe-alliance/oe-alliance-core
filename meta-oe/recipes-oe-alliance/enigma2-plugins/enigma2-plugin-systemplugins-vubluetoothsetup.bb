DESCRIPTION = "VuPLus bluetooth plugin"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "^(vusolo4k|vuuno4k|vuzero4k|vuuno4kse|vuultimo4k|vuduo4k|vuduo4kse)$"

PN = "enigma2-plugin-systemplugins-bluetoothsetup"

DEPENDS = "libusb-compat"
RDEPENDS:${PN} = "libusb-compat vuplus-bluetooth-util-${MACHINE}"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/VuBluetoothSetup.git;protocol=https;branch=main"

do_install:append() {
    install -d ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/BluetoothSetup
    install -m 0755 ${S}/VuBluetoothSetup/${MACHINE}/_vubt.so ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/BluetoothSetup/_vubt.so
}

FILES:${PN} += "/usr/lib/enigma2/python/Plugins/SystemPlugins/BluetoothSetup/"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"