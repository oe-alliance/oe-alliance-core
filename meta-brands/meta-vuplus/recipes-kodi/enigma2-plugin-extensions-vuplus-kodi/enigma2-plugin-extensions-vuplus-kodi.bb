DESCRIPTION = "Enigma2 plugin to launch and play media from Kodi"
SECTION = "base"
LICENSE = "CLOSED"
require conf/license/license-close.inc

SRCDATE = "20160331_r0"

PV = "1.0"
PR = "${SRCDATE}_r0"

COMPATIBLE_MACHINE = "^(vuduo2|vusolo2|vusolose|vusolo4k|vuultimo4k|vuuno4k)$"

DEPENDS += "enigma2 virtual/kodi"
RDEPENDS_${PN} += "virtual/kodi"

RRECOMMENDS_${PN} = "enigma2-plugin-extensions-subssupport"

PROVIDES += "enigma2-plugin-extensions-kodi"
RPROVIDES_${PN} += "enigma2-plugin-extensions-kodi"

SRC_URI = "http://archive.vuplus.com/download/build_support/kodi/e2xbmc-plugin_${SRCDATE}.tar.gz"

S = "${WORKDIR}/plugin"

PLUGIN_DIR="${D}${libdir}/enigma2/python/Plugins/Extensions/Kodi"

do_install() {
	install -d ${PLUGIN_DIR}
	install -m 0755 ${S}/*.py ${PLUGIN_DIR}
	install -m 0755 ${S}/button.png ${PLUGIN_DIR}
}

do_package_qa() {
}

FILES_${PN}="/"

SRC_URI[md5sum] = "670c29a6d7fe8cb09545e929196b6734"
SRC_URI[sha256sum] = "58920ae07aa2f935205b1ea2c37389d5176714cef470a29466974dbf7b59750a"
