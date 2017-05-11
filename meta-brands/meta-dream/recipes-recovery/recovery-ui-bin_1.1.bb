SUMMARY = "Simple user interface for Dreambox rescue mode"
HOMEPAGE = "http://dreamboxupdate.com/"
LICENSE = "CLOSED"
DEPENDS = "libmnl xz libnexus-ui"
PROVIDES = "recovery-ui"

inherit opendreambox-precompiled-binary3 update-rc.d

SRC_URI[dm520.md5sum] = "c7a2cca873f7665c9c0023d46263ea78"
SRC_URI[dm520.sha256sum] = "9436ad03565e970f6efde52969a646c82cf61bb3631e8f9f66fff8de02c7d521"
SRC_URI[dm900.md5sum] = "b8c5ce8c4047360382609a80fec85d5e"
SRC_URI[dm900.sha256sum] = "9c75d63d3edebad946c92bab2f72f73513505b24dc95442c82ef04894ff3d12a"

PACKAGE_ARCH = "${MACHINE_ARCH}"

RRECOMMENDS_${PN} = "dreambox-dvb-modules-${MACHINE}-lcd"
RPROVIDES_${PN} = "recovery-ui"

INITSCRIPT_NAME = "recovery-ui"

PRECOMPILED_NAME = "recovery-ui"
