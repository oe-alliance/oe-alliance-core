SUMMARY = "pbnigma-klibs"
MAINTAINER = "PB-Powerboard Team"
LICENSE = "GPLv2"
SECTION = "base"
PRIORITY = "required"
PACKAGE_ARCH = "${MACHINE_ARCH}"

### TODO: this is really low and dirty, use separate dir and preload or ldconfig 

require conf/license/license-gplv2.inc

inherit lib_package 

PR = "r1"


INHIBIT_PACKAGE_STRIP = "1"

SRC_URI="file://kodil.tgz"

S = "${WORKDIR}"


do_install() {
    install -d ${D}${libdir}
    install -d ${D}${sysconfdir}/shairplay

    cp -ax ${S}/usr/lib/* ${D}${libdir}/
    cp -ax ${S}/etc/shairplay/* ${D}${sysconfdir}/shairplay/
}

FILES_${PN} = "${libdir}/* ${sysconfdir}/*"
FILES_${PN}-dev = ""

do_compile() {
}

sysroot_stage_all() {
    :
}

do_package_qa() {
    exit 0
}

do_rm_work(){
}

