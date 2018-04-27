SUMMARY = "meta file for USB DVB drivers"
inherit packagegroup

require conf/license/license-gplv2.inc

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
    ${@bb.utils.contains("BRAND_OEM", "vuplus", "", "enigma2-plugin-drivers-dvb-usb-a867", d)} \
    enigma2-plugin-drivers-dvb-usb-rtl2832 \
    enigma2-plugin-drivers-dvb-usb-tbs \
    enigma2-plugin-drivers-dvb-usb-tbs5520 \
    enigma2-plugin-drivers-dvb-usb-opticombo \
    enigma2-plugin-drivers-ct2-dvb-usb-geniatech-t230 \
    enigma2-plugin-drivers-s2-dvb-usb-s960 \
    enigma2-plugin-drivers-ct2-dvb-usb-t330 \
    enigma2-plugin-drivers-ct2-dvb-usb-pctv292e \
    enigma2-plugin-drivers-dvb-usb-mn8847x \
    ${@bb.utils.contains("DEFAULTTUNE", "sh4", "" , "enigma2-plugin-drivers-dvb-usb-dvbsky-classic", d)} \
    ${@bb.utils.contains("MACHINE", "vuduo", "enigma2-plugin-drivers-dvb-usb-turbo", "", d)} \
    ${@bb.utils.contains("MACHINE", "vuduo2", "enigma2-plugin-drivers-dvb-usb-turbo", "", d)} \
    ${@bb.utils.contains("MACHINE", "vusolo", "enigma2-plugin-drivers-dvb-usb-turbo", "", d)} \
    ${@bb.utils.contains("MACHINE", "vusolo2", "enigma2-plugin-drivers-dvb-usb-turbo", "", d)} \
    ${@bb.utils.contains("MACHINE", "vusolose", "enigma2-plugin-drivers-dvb-usb-turbo", "", d)} \
    ${@bb.utils.contains("MACHINE", "vuultimo", "enigma2-plugin-drivers-dvb-usb-turbo", "", d)} \
    ${@bb.utils.contains("MACHINE", "vuuno", "enigma2-plugin-drivers-dvb-usb-turbo", "", d)} \
    ${@bb.utils.contains("MACHINE", "vuzero", "enigma2-plugin-drivers-dvb-usb-turbo", "", d)} \
    ${@bb.utils.contains("MACHINE", "vusolo4k", "enigma2-plugin-drivers-dvb-usb-turbo", "", d)} \
    ${@bb.utils.contains("MACHINE", "vuuno4k", "enigma2-plugin-drivers-dvb-usb-turbo", "", d)} \
    ${@bb.utils.contains("MACHINE", "vuuno4kse", "enigma2-plugin-drivers-dvb-usb-turbo", "", d)} \
    ${@bb.utils.contains("MACHINE", "vuultimo4k", "enigma2-plugin-drivers-dvb-usb-turbo", "", d)} \
    ${@bb.utils.contains("MACHINE", "vuzero4k", "enigma2-plugin-drivers-dvb-usb-turbo", "", d)} \
    ${@bb.utils.contains("MACHINE", "xc7346", "enigma2-plugin-drivers-dvb-edicombo", "", d)} \
    ${@bb.utils.contains("MACHINE", "xc7362", "enigma2-plugin-drivers-dvb-edicombo", "", d)} \
    ${@bb.utils.contains("MACHINE", "dags7335", "enigma2-plugin-drivers-dvb-edicombo", "", d)} \
    ${@bb.utils.contains("MACHINE", "dags7356", "enigma2-plugin-drivers-dvb-edicombo", "", d)} \
    "

PR = "r19"
