SUMMARY = "dreambox u-boot-bin"
SECTION = "base"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PR = "r1"

PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS:${PN} = "flash-scripts"

SRC_URI = "https://source.mynonpublic.com/dreambox/uboot-bin_3.3.r1.zip"

SRC_URI[md5sum] = "8ff80679877987823ea89c8d3ecec07b"
SRC_URI[sha256sum] = "69d5dab3c4b670e182fbff9824c4b08583d15cc92df3cf1908f9ac6df74a6a45"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

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
