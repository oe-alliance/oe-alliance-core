SUMMARY = "USB controller and demodulator firmware for the TBS5927SE"
require conf/license/license-close.inc
require tbs5927se-vendor.inc

inherit allarch

do_configure[noexec] = "1"
do_compile[noexec] = "1"

PACKAGES = "${PN} firmware-dvb-usb-tbs5927se firmware-dvb-demod-avl62x1"
ALLOW_EMPTY:${PN} = "1"
RRECOMMENDS:${PN} = "firmware-dvb-usb-tbs5927se firmware-dvb-demod-avl62x1"

FILES:firmware-dvb-usb-tbs5927se = "${nonarch_base_libdir}/firmware/dvb-usb-id5927se.fw"
FILES:firmware-dvb-demod-avl62x1 = "${nonarch_base_libdir}/firmware/dvb-demod-avl62x1.fw"

do_install() {
	install -d ${D}${nonarch_base_libdir}/firmware
	tar --no-same-owner -xjf ${S}/tbs-tuner-firmwares_v1.0.tar.bz2 \
		-C ${D}${nonarch_base_libdir}/firmware \
		./dvb-usb-id5927se.fw ./dvb-demod-avl62x1.fw
	chmod 0644 ${D}${nonarch_base_libdir}/firmware/*.fw
}
