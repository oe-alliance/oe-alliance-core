SUMMARY = "tool to switch multidevice usb modes"
require conf/license/license-gplv2.inc

LICENSE = "GPLv3"

PV="20120930"

SRC_URI[md5sum] = "09191733fe39d65213dad68f8f385eac"
SRC_URI[sha256sum] = "0d82f8692d11b3f6614ac875b4096725692b79b9f4fe500fbe8497b601202694"

SRC_URI +=" http://code-ini.com/software/tools/usb-modeswitch-data-${PV}.tar.gz \
        file://usb-modeswitch-data_20120215.patch \
        file://usb-modeswitch-data-20120930.patch \
        "

S = "${WORKDIR}/usb-modeswitch-data-${PV}"

do_compile() {
}

do_install() {
    install -d ${D}/usr/share/usb_modeswitch
    #install -d ${D}/etc/usb_modeswitch.d
    #install -d ${D}/lib/udev/rules.d
    #install -m 0755 ${S}/40-usb_modeswitch.rules ${D}/lib/udev/rules.d/
    #install -m 0755 ${S}/usb_modeswitch.d/* ${D}/usr/share/usb_modeswitch/
    DESTDIR=${D} make install
    install -m 0755 ${S}/usb_modeswitch.d/* ${D}/usr/share/usb_modeswitch/
}

FILES_${PN} += "/etc /lib /usr"
