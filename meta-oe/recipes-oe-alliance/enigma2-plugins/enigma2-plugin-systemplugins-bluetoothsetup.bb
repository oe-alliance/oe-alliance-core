DESCRIPTION = "Vuplus bluetooth plugin"

LICENSE = "CLOSED"

DEPENDS = "python-native"
RDEPENDS_${PN} = "vuplus-bluetooth-util"

SRCDATE="20170327"
SRCDATE_PR = "r0"
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

SRC_URI[md5sum] = "a1d19dd1a8b739b4073f4da07b6c1ccc"
SRC_URI[sha256sum] = "94ec17b623fc8178b8d6083e516d12b047d698107c19eda39c9b0250a637df46"
