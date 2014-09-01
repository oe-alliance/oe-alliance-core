SUMMARY = "meta file for USB DVB drivers"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

DEPENDS = "\
    enigma2-plugin-drivers-dvb-usb-dib0700 \
    enigma2-plugin-drivers-dvb-usb-af9015 \
    enigma2-plugin-drivers-dvb-usb-siano \
    enigma2-plugin-drivers-dvb-usb-em28xx \
    enigma2-plugin-drivers-dvb-usb-dw2102 \
    enigma2-plugin-drivers-dvb-usb-as102 \
    enigma2-plugin-drivers-dvb-usb-it913x \
    enigma2-plugin-drivers-dvb-usb-pctv452e \
    enigma2-plugin-drivers-dvb-usb-dtt200u \
    enigma2-plugin-drivers-dvb-usb-af9035 \
    enigma2-plugin-drivers-dvb-usb-a867 \
    ${@base_contains("TARGET_ARCH", "sh4", "" , "enigma2-plugin-drivers-dvb-usb-dvbsky", d)} \
    enigma2-plugin-drivers-dvb-usb-rtl2832 \
    "

PR = "r7"
