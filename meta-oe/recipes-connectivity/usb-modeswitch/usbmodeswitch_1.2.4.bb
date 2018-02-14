SUMMARY = "tool to switch multidevice usb modes"
require conf/license/license-gplv2.inc

LICENSE = "GPLv3"
DEPENDS = "libusb"

SRC_URI[md5sum] = "dbd4ce7966d7b4a5a0604a8280f7164d"
SRC_URI[sha256sum] = "d772b7438bb2efb524ab4c612198e4f4d4505e25b809d1d1ed4232a37ed78972"

PV = "1.2.4"

SRC_URI = "https://src.fedoraproject.org/repo/pkgs/usb_modeswitch/usb-modeswitch-1.2.4.tar.bz2/dbd4ce7966d7b4a5a0604a8280f7164d/usb-modeswitch-1.2.4.tar.bz2 \
    file://makefile.patch \
"

S = "${WORKDIR}/usb-modeswitch-${PV}"

do_install() {
        install -d ${D}${bindir}
        install -m 0755 ${S}/usb_modeswitch ${D}${bindir}
}

