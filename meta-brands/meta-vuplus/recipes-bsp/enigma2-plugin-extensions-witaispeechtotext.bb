DESCRIPTION = "Vuplus wit.ai speech to text plugin"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

DEPENDS = "python-native"
RDEPENDS_${PN} = "python-requests bluetoothsetup-${MACHINE}"

SRCDATE="20170327"
SRCDATE_PR = "r0"
PR="${SRCDATE}.${SRCDATE_PR}"

SRC_URI = " \
    http://archive.vuplus.com/download/build_support/vuplus/enigma2-plugin-witaispeechtotext-${PR}.tar.gz \
"

S = "${WORKDIR}/enigma2-plugin-witaispeechtotext"

WITAISPEECHTOTEXT_PLUGIN_PATH = "/usr/lib/enigma2/python/Plugins/Extensions/WitAiSpeechToText"

do_compile() {
	python -O -m compileall ${S}
}

do_install() {
	install -d  ${D}${WITAISPEECHTOTEXT_PLUGIN_PATH}
	cp -rp ${S}/* ${D}${WITAISPEECHTOTEXT_PLUGIN_PATH}
}

FILES_${PN} = "${WITAISPEECHTOTEXT_PLUGIN_PATH}"

do_populate_sysroot[noexec] = "1"

PACKAGE_ARCH = "${MACHINE_ARCH}"

INSANE_SKIP_${PN} = "already-stripped"

SRC_URI[md5sum] = "6b1fe4fd6f921a08721e4613b88da485"
SRC_URI[sha256sum] = "cfb889fc95a3223783d9448abeaa37b5d17144d19c6278c8d9f24851f51dddb9"

