SUMMARY = "preinstall several driver packages"
LICENSE = "MIT"
PACKAGE_ARCH = "${MACHINE_ARCH}"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit packagegroup

PV = "5.2"
PR = "r3"

ALLOW_EMPTY_${PN} = "1"

DEPENDS = "enigma2 network-usb-drivers-meta"

DEPENDS_remove_wetekplay = "network-usb-drivers-meta"
DEPENDS_remove_wetekplay2 = "network-usb-drivers-meta"

RDEPENDS_${PN}_gbquad = "enigma2-plugin-drivers-network-usb-smsc75xx"
RDEPENDS_${PN}_gbquadplus = "enigma2-plugin-drivers-network-usb-smsc75xx"

RRECOMMENDS_${PN}_openbh = " \
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
    ${@base_contains("BRAND_OEM", "vuplus", "", "enigma2-plugin-drivers-dvb-usb-a867", d)} \
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

RRECOMMENDS_${PN}_egami = " \
    enigma2-plugin-drivers-dvb-usb-af9015 \
    enigma2-plugin-drivers-dvb-usb-as102 \
    enigma2-plugin-drivers-dvb-usb-it913x \
    enigma2-plugin-drivers-dvb-usb-af9035 \
    enigma2-plugin-drivers-dvb-usb-rtl2832 \
"