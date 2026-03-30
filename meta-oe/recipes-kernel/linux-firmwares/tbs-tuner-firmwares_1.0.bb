SUMMARY = "Firmware collection for TBS USB DVB tuners"
HOMEPAGE = "https://www.tbsdtv.com/"
require conf/license/license-close.inc

SRC_URI = "https://www.tbsdtv.com/download/document/linux/tbs-tuner-firmwares_v1.0.tar.bz2"
SRC_URI[md5sum] = "43e9cb4f0bb97e60213774169dab4c86"
SRC_URI[sha256sum] = "972f3e26c88c51252655f028e79abb3c53f085cfb96551f86a8a678c963e2d4e"

S = "${UNPACKDIR}"

inherit allarch

PACKAGES = " \
    ${PN} \
    firmware-dvb-usb-tbs5931 \
    firmware-dvb-usb-tbs5927 \
    firmware-dvb-usb-tbs5530 \
    firmware-dvb-usb-tbs5580 \
    firmware-dvb-usb-tbs5520 \
    firmware-dvb-usb-tbs5520se \
    firmware-dvb-usb-tbs5590 \
    firmware-dvb-usb-tbs5881 \
    firmware-dvb-usb-tbs5925 \
    firmware-dvb-usb-tbs5980 \
    firmware-dvb-demod-m88rs6060 \
    firmware-dvb-demod-si2183 \
    firmware-tbs-cx231xx \
"

ALLOW_EMPTY:${PN} = "1"
RRECOMMENDS:${PN} = " \
    firmware-dvb-usb-tbs5931 \
    firmware-dvb-usb-tbs5927 \
    firmware-dvb-usb-tbs5530 \
    firmware-dvb-usb-tbs5580 \
    firmware-dvb-usb-tbs5520 \
    firmware-dvb-usb-tbs5520se \
    firmware-dvb-usb-tbs5590 \
    firmware-dvb-usb-tbs5881 \
    firmware-dvb-usb-tbs5925 \
    firmware-dvb-usb-tbs5980 \
    firmware-dvb-demod-m88rs6060 \
    firmware-dvb-demod-si2183 \
    firmware-tbs-cx231xx \
"

FILES:firmware-dvb-usb-tbs5931 = "${nonarch_base_libdir}/firmware/dvb-usb-id5931.fw"
FILES:firmware-dvb-usb-tbs5927 = "${nonarch_base_libdir}/firmware/dvb-usb-tbsqbox-id5927.fw"
FILES:firmware-dvb-usb-tbs5530 = "${nonarch_base_libdir}/firmware/dvb-usb-id5530.fw"
FILES:firmware-dvb-usb-tbs5580 = "${nonarch_base_libdir}/firmware/dvb-usb-id5580.fw"
FILES:firmware-dvb-usb-tbs5520 = "${nonarch_base_libdir}/firmware/dvb-usb-tbsqbox-id5520.fw"
FILES:firmware-dvb-usb-tbs5520se = "${nonarch_base_libdir}/firmware/dvb-usb-id5520se.fw"
FILES:firmware-dvb-usb-tbs5590 = "${nonarch_base_libdir}/firmware/dvb-usb-id5590.fw"
FILES:firmware-dvb-usb-tbs5881 = "${nonarch_base_libdir}/firmware/dvb-usb-tbsqbox-id5881.fw"
FILES:firmware-dvb-usb-tbs5925 = "${nonarch_base_libdir}/firmware/dvb-usb-tbsqbox-id5925.fw"
FILES:firmware-dvb-usb-tbs5980 = "${nonarch_base_libdir}/firmware/dvb-usb-tbsqbox-id5980.fw"
FILES:firmware-dvb-demod-m88rs6060 = "${nonarch_base_libdir}/firmware/dvb-demod-m88rs6060.fw"
FILES:firmware-dvb-demod-si2183 = "${nonarch_base_libdir}/firmware/dvb-demod-si2183-b60-01.fw"
FILES:firmware-tbs-cx231xx = "${nonarch_base_libdir}/firmware/v4l-cx231xx-avcore-01.fw"

do_install() {
	install -d ${D}${nonarch_base_libdir}/firmware
	for firmware in \
		dvb-usb-id5931.fw \
		dvb-usb-tbsqbox-id5927.fw \
		dvb-usb-id5530.fw \
		dvb-usb-id5580.fw \
		dvb-usb-tbsqbox-id5520.fw \
		dvb-usb-id5520se.fw \
		dvb-usb-id5590.fw \
		dvb-usb-tbsqbox-id5881.fw \
		dvb-usb-tbsqbox-id5925.fw \
		dvb-usb-tbsqbox-id5980.fw \
		dvb-demod-m88rs6060.fw \
		dvb-demod-si2183-b60-01.fw \
		v4l-cx231xx-avcore-01.fw; do
		install -m 0644 ${S}/$firmware ${D}${nonarch_base_libdir}/firmware/
	done
}
