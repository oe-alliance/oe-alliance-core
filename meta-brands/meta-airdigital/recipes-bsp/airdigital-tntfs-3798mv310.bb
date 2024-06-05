SUMMARY = "Hardware drivers for TNTFS"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

KV = "4.4.176"
SRCDATE = "20200528"

SRC_URI[md5sum] = "37c7f9981127b214711c49a28dab211b"
SRC_URI[sha256sum] = "5741c9f80afe101ba3ef92010eec2a7596546c998ed8e9de847648f85f19f57e"

COMPATIBLE_MACHINE = "^h11$"

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