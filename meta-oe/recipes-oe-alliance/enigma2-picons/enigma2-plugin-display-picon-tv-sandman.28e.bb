SUMMARY = "Sandman 28E vfd picons"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "Sandman"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

SRC_URI[md5sum] = "93e780e655d30f7cf46cf46685b9da9d"
SRC_URI[sha256sum] = "012dde6fb4e4bc95955f22a2423d6b1a763b011463b0f42c10c06c3ff6028c50"

RCONFLICTS_${PN} = "enigma2-plugin-display-picon-tv-sandman.ev0.28e"
RREPLACES_${PN} = "enigma2-plugin-display-picon-tv-sandman.ev0.28e"
RCONFLICTS_${PN} = "enigma2-plugin-display-picon-tv-rossi2000.28.2e"
RREPLACES_${PN} = "enigma2-plugin-display-picon-tv-rossi2000.28.2e"

SRCDATE = "20120309"
PV = "${SRCDATE}"
PR = "r6"
SETTINGS_FILENAME = "sandman.28e.vfd.picons"

SRC_URI = "http://openvix.co.uk/feeds_extras/picons/${SETTINGS_FILENAME}_${PV}.tar.gz"

S = "${WORKDIR}/lcd_picon"

inherit autotools pkgconfig

PACKAGES = "${PN}"

FILES_${PN} = "/lcd_picon"

do_install() {
    install -d ${D}/lcd_picon
    install -m 0644 ${WORKDIR}/lcd_picon/*.* ${D}/lcd_picon
}

do_install_append() {
    find ${D}/lcd_picon/ -name 'picon_default.png' -exec rm {} \;
}
