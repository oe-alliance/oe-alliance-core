SUMMARY = "Hardware drivers for TNTFS"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

KV = "4.4.176"
SRCDATE = "20200528"

SRC_URI[md5sum] = "ddd44a9b96f57fb6e0e838ff7b9a495c"
SRC_URI[sha256sum] = "3ca38c87ba88a53582fdb1ffcd93a4c7b08764da04afda8bdf65678be5d3b3df"

COMPATIBLE_MACHINE = "^h8$|^hzero$"

SRC_URI = "https://source.mynonpublic.com/tntfs/${HICHIPSET}-tntfs-${KV}-${SRCDATE}.zip"

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