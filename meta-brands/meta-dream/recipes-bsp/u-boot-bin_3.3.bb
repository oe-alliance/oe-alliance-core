SUMMARY = "dreambox u-boot-bin"
SECTION = "base"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PR = "r0"

PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS:${PN} = "flash-scripts"

SRC_URI = "https://source.mynonpublic.com/dreambox/uboot-bin_3.3.zip"

SRC_URI[md5sum] = "0c8258062fdc8e45704cb2097217c121"
SRC_URI[sha256sum] = "c8365e5d05afb53b851fa4f6be519af5c34e8ea9bb3cc6369a07b4b7b92d4574"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

do_compile() {
}
do_populate_sysroot() {
}

do_install() {
    install -d ${D}/usr
    cp -rp --no-preserve=ownership ${S}/usr/* ${D}/usr/
    chmod -R 755 ${D}/usr/share
}

FILES:${PN} += "/usr"
