SUMMARY = "Hardware drivers for TNTFS"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

KV = "4.4.176"
SRCDATE = "20221203"

SRC_URI[md5sum] = "05ad1af9ba470d7208456cd64c070357"
SRC_URI[sha256sum] = "bf41912a40a3f9e8e86bea839ed4e8483b23b971fe878ed4371ae1a853076208"

SRC_URI = "https://source.mynonpublic.com/tntfs/${HICHIPSET}-tntfs-${SRCDATE}.zip"

S = "${WORKDIR}"

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

FILES_${PN} += "${sysconfdir}/modules-load.d/tntfs.conf /lib/modules/${KV}/extra"
