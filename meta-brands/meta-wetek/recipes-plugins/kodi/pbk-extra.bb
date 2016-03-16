SUMMARY = "pbnigma-kextra"
MAINTAINER = "PB-Powerboard Team"
LICENSE = "GPLv2"
SECTION = "base"
PRIORITY = "required"
PACKAGE_ARCH = "${MACHINE_ARCH}"

#PV = "1"
PR = "r5"

require conf/license/license-gplv2.inc

INHIBIT_PACKAGE_STRIP = "1"

SRC_URI="file://kodi.tgz"

S = "${WORKDIR}"

do_install() {
    install -d ${D}/usr/share/kodi
    cp -axr ${S}/share/kodi ${D}/usr/share
    cp -ax ${S}/share/xbmc ${D}/usr/share
}

FILES_${PN} = "/usr/share/*"

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
