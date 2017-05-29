DESCRIPTION = "Vuplus bluetooth plugin"

LICENSE = "CLOSED"

DEPENDS = "python-native"
RDEPENDS_${PN} = "vuplus-bluetooth-util"

SRCDATE="20170523"
SRCDATE_PR = "r1"
PR="${SRCDATE}.${SRCDATE_PR}"

SRC_URI = " \
    http://archive.vuplus.com/download/build_support/vuplus/enigma2-plugin-bluetoothsetup-${MACHINE}-${PR}.tar.gz \
"

S = "${WORKDIR}/plugin"

BLUETOOTH_PLUGIN_PATH = "/usr/lib/enigma2/python/Plugins/SystemPlugins/BluetoothSetup"

do_compile() {
	python -O -m compileall ${S}
}

do_install() {
	install -d  ${D}${BLUETOOTH_PLUGIN_PATH}
	cp -rp ${S}/* ${D}${BLUETOOTH_PLUGIN_PATH}
}

FILES_${PN} = "${BLUETOOTH_PLUGIN_PATH}"

do_populate_sysroot[noexec] = "1"

PACKAGE_ARCH = "${MACHINE_ARCH}"

INSANE_SKIP_${PN} = "already-stripped"

SRC_URI[md5sum] = "3c641e74286cf2c7b315308801cf6b7a"
SRC_URI[sha256sum] = "11601ed683c538b4824f0ddbe1613a887cf28ea46ba9e0c706ac662854f327db"
