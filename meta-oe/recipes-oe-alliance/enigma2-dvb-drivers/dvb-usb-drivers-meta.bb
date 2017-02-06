SUMMARY = "meta file for USB DVB drivers"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

ALLOW_EMPTY_${PN} = "1"

DEPENDS = "\
    enigma2-plugin-drivers-atsc-usb-hauppauge \
    enigma2-plugin-drivers-atsc-950q-usb-hauppauge \
    enigma2-plugin-drivers-atsc-955q-usb-hauppauge \
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
    enigma2-plugin-drivers-dvb-usb-rtl2832 \
    enigma2-plugin-drivers-dvb-usb-tbs \
    enigma2-plugin-drivers-dvb-usb-opticombo \
    enigma2-plugin-drivers-ct2-dvb-usb-geniatech-t230 \
    enigma2-plugin-drivers-s2-dvb-usb-s960 \
    enigma2-plugin-drivers-ct2-dvb-usb-t330 \
    ${@base_contains("TARGET_ARCH", "sh4", "" , "enigma2-plugin-drivers-dvb-usb-dvbsky-classic", d)} \
    ${@base_contains("MACHINE", "vuduo", "enigma2-plugin-drivers-dvb-usb-turbo", "", d)} \
    ${@base_contains("MACHINE", "vuduo2", "enigma2-plugin-drivers-dvb-usb-turbo", "", d)} \
    ${@base_contains("MACHINE", "vusolo", "enigma2-plugin-drivers-dvb-usb-turbo", "", d)} \
    ${@base_contains("MACHINE", "vusolo2", "enigma2-plugin-drivers-dvb-usb-turbo", "", d)} \
    ${@base_contains("MACHINE", "vusolose", "enigma2-plugin-drivers-dvb-usb-turbo", "", d)} \
    ${@base_contains("MACHINE", "vuultimo", "enigma2-plugin-drivers-dvb-usb-turbo", "", d)} \
    ${@base_contains("MACHINE", "vuuno", "enigma2-plugin-drivers-dvb-usb-turbo", "", d)} \
    ${@base_contains("MACHINE", "vuzero", "enigma2-plugin-drivers-dvb-usb-turbo", "", d)} \
    ${@base_contains("MACHINE", "vusolo4k", "enigma2-plugin-drivers-dvb-usb-turbo", "", d)} \
    ${@base_contains("MACHINE", "vuuno4k", "enigma2-plugin-drivers-dvb-usb-turbo", "", d)} \
    ${@base_contains("MACHINE", "vuultimo4k", "enigma2-plugin-drivers-dvb-usb-turbo", "", d)} \
    "


PR = "r16"
