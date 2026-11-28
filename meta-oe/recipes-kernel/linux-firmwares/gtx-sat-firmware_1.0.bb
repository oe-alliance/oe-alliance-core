SUMMARY = "Montage M88RS6060 satellite demodulator firmware"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "${AMLOGIC_SOURCE_MIRROR}/dvb-demod-m88rs6060.fw"
SRC_URI[sha256sum] = "848d7974ee768fed096016abed58414c8ca3c041eb7d6af09883760ff906f080"
S = "${UNPACKDIR}"

do_install() {
    install -Dm0644 ${S}/dvb-demod-m88rs6060.fw ${D}${nonarch_base_libdir}/firmware/gtmedia/gtxcombo/dvb-demod-m88rs6060.fw
}
FILES:${PN} = "${nonarch_base_libdir}/firmware/gtmedia/gtxcombo/dvb-demod-m88rs6060.fw"
