SUMMARY = "tool to switch multidevice usb modes"
require conf/license/license-gplv2.inc

LICENSE = "GPLv3"

PV="20131113"
PR = "r1"

SRC_URI[md5sum] = "7b5ac1226b360ddc366c286e62b3c3a4"
SRC_URI[sha256sum] = "b3213a460837bd52dad6c8675eda348347f65a034da53a1eaebdaeabf17e2137"

SRC_URI +=" https://src.fedoraproject.org/repo/pkgs/usb_modeswitch-data/usb-modeswitch-data-20131113.tar.bz2/7b5ac1226b360ddc366c286e62b3c3a4/usb-modeswitch-data-20131113.tar.bz2 \
    file://usb-modeswitch-data_20120215.patch \
    file://usbmodeswitch-data_20131214.patch \
    file://usbmodeswitch-data_20131217.patch \
    "

S = "${WORKDIR}/usb-modeswitch-data-${PV}"

do_compile() {
}

do_install() {
    install -d ${D}/usr/share/usb_modeswitch
    DESTDIR=${D} make install
    install -m 0755 ${S}/usb_modeswitch.d/* ${D}/usr/share/usb_modeswitch/
}

FILES_${PN} += "/etc /lib /usr"
