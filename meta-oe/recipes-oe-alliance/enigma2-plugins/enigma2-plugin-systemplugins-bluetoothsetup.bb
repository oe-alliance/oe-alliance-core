DESCRIPTION = "Vuplus bluetooth plugin"

LICENSE = "CLOSED"

DEPENDS = "python-native"
RDEPENDS_${PN} = "vuplus-bluetooth-util"

SRCDATE="20170727"
SRCDATE_PR = "r3"
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

SRC_URI[md5sum] = "064e1d783aba20cf3319f133269b8d70"
SRC_URI[sha256sum] = "3212e1208a556a6d222aa94d67e783fa170a68c724968eadf20f40171ef7e83b"
