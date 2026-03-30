SUMMARY = "meta file for USB DVB drivers"
PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit packagegroup

require conf/license/license-gplv2.inc

TBS_OUT_OF_TREE_USB_DRIVERS_SUPPORTED = " \
    enigma2-plugin-drivers-dvb-usb-tbs5520se \
    enigma2-plugin-drivers-dvb-usb-tbs5530 \
    enigma2-plugin-drivers-dvb-usb-tbs5580 \
    enigma2-plugin-drivers-dvb-usb-tbs5590-v2 \
    enigma2-plugin-drivers-dvb-usb-tbs5881 \
    enigma2-plugin-drivers-dvb-usb-tbs5925 \
    enigma2-plugin-drivers-dvb-usb-tbs5927 \
    enigma2-plugin-drivers-dvb-usb-tbs5930-lite \
    enigma2-plugin-drivers-dvb-usb-tbs5980 \
    enigma2-plugin-drivers-dvb-usb-tbs5990 \
    "

# The current TBS vendor sources require DVBv5 frontend statistics which are
# absent from the receiver kernels tested at 3.2 and 3.4.  Linux 3.13 is the
# oldest successfully built API baseline.  Keep the legacy in-kernel TBS
# packages below available on older receivers.
TBS_OUT_OF_TREE_USB_DRIVERS = "${@d.getVar('TBS_OUT_OF_TREE_USB_DRIVERS_SUPPORTED') if bb.utils.vercmp_string_op(d.getVar('KERNEL_VERSION') or '0', '3.13', '>=') else ''}"

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
    ${TBS_OUT_OF_TREE_USB_DRIVERS} \
    enigma2-plugin-drivers-dvb-usb-opticombo \
    enigma2-plugin-drivers-ct2-dvb-usb-geniatech-t230 \
    enigma2-plugin-drivers-s2-dvb-usb-s960 \
    enigma2-plugin-drivers-ct2-dvb-usb-t330 \
    enigma2-plugin-drivers-ct2-dvb-usb-pctv292e \
    enigma2-plugin-drivers-dvb-usb-mn8847x \
    enigma2-plugin-drivers-ct2-dvb-usb-dualhd \
    enigma2-plugin-drivers-dvb-usb-dvbsky-classic \
    ${@bb.utils.contains("MACHINE", "vuduo", "enigma2-plugin-drivers-dvb-usb-turbo", "", d)} \
    ${@bb.utils.contains("MACHINE", "vuduo2", "enigma2-plugin-drivers-dvb-usb-turbo enigma2-plugin-drivers-dvb-usb-turbo2", "", d)} \
    ${@bb.utils.contains("MACHINE", "vusolo", "enigma2-plugin-drivers-dvb-usb-turbo", "", d)} \
    ${@bb.utils.contains("MACHINE", "vusolo2", "enigma2-plugin-drivers-dvb-usb-turbo enigma2-plugin-drivers-dvb-usb-turbo2", "", d)} \
    ${@bb.utils.contains("MACHINE", "vusolose", "enigma2-plugin-drivers-dvb-usb-turbo enigma2-plugin-drivers-dvb-usb-turbo2", "", d)} \
    ${@bb.utils.contains("MACHINE", "vuultimo", "enigma2-plugin-drivers-dvb-usb-turbo", "", d)} \
    ${@bb.utils.contains("MACHINE", "vuuno", "enigma2-plugin-drivers-dvb-usb-turbo", "", d)} \
    ${@bb.utils.contains("MACHINE", "vuzero", "enigma2-plugin-drivers-dvb-usb-turbo enigma2-plugin-drivers-dvb-usb-turbo2", "", d)} \
    ${@bb.utils.contains("MACHINE", "vusolo4k", "enigma2-plugin-drivers-dvb-usb-turbo enigma2-plugin-drivers-dvb-usb-turbo2", "", d)} \
    ${@bb.utils.contains("MACHINE", "vuuno4k", "enigma2-plugin-drivers-dvb-usb-turbo enigma2-plugin-drivers-dvb-usb-turbo2", "", d)} \
    ${@bb.utils.contains("MACHINE", "vuuno4kse", "enigma2-plugin-drivers-dvb-usb-turbo enigma2-plugin-drivers-dvb-usb-turbo2", "", d)} \
    ${@bb.utils.contains("MACHINE", "vuultimo4k", "enigma2-plugin-drivers-dvb-usb-turbo enigma2-plugin-drivers-dvb-usb-turbo2", "", d)} \
    ${@bb.utils.contains("MACHINE", "vuzero4k", "enigma2-plugin-drivers-dvb-usb-turbo enigma2-plugin-drivers-dvb-usb-turbo2", "", d)} \
    ${@bb.utils.contains("MACHINE", "vuduo4k", "enigma2-plugin-drivers-dvb-usb-turbo enigma2-plugin-drivers-dvb-usb-turbo2", "", d)} \
    ${@bb.utils.contains("MACHINE", "vuduo4kse", "enigma2-plugin-drivers-dvb-usb-turbo enigma2-plugin-drivers-dvb-usb-turbo2", "", d)} \
    ${@bb.utils.contains("MACHINE", "xc7346", "enigma2-plugin-drivers-dvb-edicombo", "", d)} \
    ${@bb.utils.contains("MACHINE", "xc7362", "enigma2-plugin-drivers-dvb-edicombo", "", d)} \
    ${@bb.utils.contains("MACHINE", "dags7335", "enigma2-plugin-drivers-dvb-edicombo", "", d)} \
    ${@bb.utils.contains("MACHINE", "dags7356", "enigma2-plugin-drivers-dvb-edicombo", "", d)} \
    "

PR = "r0"
