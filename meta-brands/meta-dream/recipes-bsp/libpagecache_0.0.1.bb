SUMMARY = "Preloadable library to improve large file operations"
SECTION = "base"
LICENSE = "CLOSED"
DEPENDS = "libdlsym"
SRCREV = "108ef8c6337ec803467428a2d0744c6db9772928"
PR = "r0"
require conf/license/license-close.inc

inherit autotools opendreambox-git

FILES:${PN} = "${libdir}/libpagecache.so"
RREPLACES:${PN} += "libpagecache0"
RCONFLICTS:${PN} += "libpagecache0"

#no more use libpagecache as default preload lib
#do_install:append() {
#        install -d ${D}${sysconfdir}
#        echo "${libdir}/libpagecache.so.0.0.0" > ${D}${sysconfdir}/ld.so.preload
#}

pkg_postinst:${PN} () {
[ -z $D ] && sed -i 's|${libdir}/libpagecache.so.0.0.0||g' ${sysconfdir}/ld.so.preload || /bin/true
}

pkg_postrm:${PN} () {
[ -z $D ] && sed -i 's|${libdir}/libpagecache.so.0.0.0||g' ${sysconfdir}/ld.so.preload || /bin/true
}
