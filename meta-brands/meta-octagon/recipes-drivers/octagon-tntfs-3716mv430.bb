SUMMARY = "Hardware drivers for TNTFS"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

KV = "4.4.176"
SRCDATE = "20220305"

SRC_URI[md5sum] = "073f3e3cc4f74f0d81770fc8978e2522"
SRC_URI[sha256sum] = "8c7d771f3416ca1de733d9544350cf65f5372d7f0306b2f1934d1e20e602c402"

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