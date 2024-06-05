SUMMARY = "Hardware drivers for TNTFS"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

KV = "4.4.176"
SRCDATE = "20211220"

SRC_URI[md5sum] = "acf3691c6c60030d49c7ae4c75f76dca"
SRC_URI[sha256sum] = "9d8f71a4d256b30c092c58b961732926b68d00b1a8940444b139afd0c702f2f4"

SRC_URI = "https://source.mynonpublic.com/tntfs/${HICHIPSET}-tntfs-${SRCDATE}.zip"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

INHIBIT_PACKAGE_STRIP = "1"

do_compile() {
}
do_populate_sysroot() {
}

do_install() {
    install -d ${D}/lib/modules/${KV}/extra
    install -d ${D}/${sysconfdir}/modules-load.d
    install -m 0755 ${S}/tntfs.ko ${D}${base_libdir}/modules/${KV}/extra
    echo tntfs >> ${D}/${sysconfdir}/modules-load.d/tntfs.conf
}

FILES:${PN} += "${sysconfdir}/modules-load.d/tntfs.conf /lib/modules/${KV}/extra"