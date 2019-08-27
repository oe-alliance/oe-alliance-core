DSCRIPTION = "GigaBlue bluetooth plugin"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

DEPENDS = "python-native"
RDEPENDS_${PN} = "libcurl libsqlite3 libssl libcrypto libudev libusb-compat"
RDEPENDS_${PN} += "gb-bluetooth-util"

PROVIDES += "enigma2-plugin-systemplugins-bluetoothsetup"
RPROVIDES_${PN} += "enigma2-plugin-systemplugins-bluetoothsetup"

RREPLACES_${PN} = "enigma2-plugin-systemplugins-bluetoothsetup"

PR="${SRCDATE}.${SRCDATE_PR}"

SRC_URI = " \
     http://source.mynonpublic.com/gigablue/bluetooth/enigma2-plugin-bluetoothsetup-${MACHINE}-${PR}.tar.gz \
"

S = "${WORKDIR}/plugin"

BLUETOOTH_PLUGIN_PATH = "/usr/lib/enigma2/python/Plugins/SystemPlugins/BluetoothSetup"

do_compile() {
	python -O -m compileall ${S}
}

do_install() {

	install -d  ${D}${BLUETOOTH_PLUGIN_PATH}
	cp -r ${S}/* ${D}${BLUETOOTH_PLUGIN_PATH}

}

do_package_qa() {
}

FILES_${PN} = "${BLUETOOTH_PLUGIN_PATH}"

do_populate_sysroot[noexec] = "1"

PACKAGE_ARCH = "${MACHINE_ARCH}"

INSANE_SKIP_${PN} = "already-stripped"

