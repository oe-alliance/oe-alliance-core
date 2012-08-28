DESCRIPTION = "tool to switch multidevice usb modes"
require conf/license/license-gplv2.inc

LICENSE = "GPL"
DEPENDS = "libusb"

SRC_URI[md5sum] = "9b29e8b0d93d7604a9e5efc4696d37a3"
SRC_URI[sha256sum] = "924bf9357241c3d2de37b381ebbe82818fa3fd19fcffdc2dcaf2b5d17d29723b"

PV = "1.2.3"

SRC_URI = "http://www.draisberghof.de/usb_modeswitch/usb-modeswitch-${PV}.tar.bz2"

S = "${WORKDIR}/usb-modeswitch-${PV}"

do_install() {
        install -d ${D}${bindir}
        install -m 0755 ${S}/usb_modeswitch ${D}${bindir}
}

