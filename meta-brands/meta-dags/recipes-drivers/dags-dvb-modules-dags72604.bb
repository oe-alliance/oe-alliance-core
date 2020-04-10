SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGE_ARCH = "${MACHINEBUILD}"

SRCDATE = "20200407"
SRCDATE_openspa = "20190327"
KV = "4.1.20"
PV = "${KV}+${SRCDATE}"
PR = "r0"

SRC_URI[md5sum] = "fcb9ecb0f77ddd616b66330ff228ed5f"
SRC_URI[sha256sum] = "f0e10f73b93ecf222094d91aca33aebb90f4fe6eb1ea1324cfdc344de3a465d2"
SRC_URI[openspa.md5sum] = "c8c3a1ecf2ff7aa3aa052259ae4c09e8"
SRC_URI[openspa.sha256sum] = "3728da01ddbd85626a332864d0fab92b73cf14e8afc05219ff75bc517754e0c2"

SRC_URI = "http://en3homeftp.net/release/images/oedrivers/bcmlinuxdvb_72604-${KV}-${SRCDATE}.tar.gz"
SRC_URI_openspa = "http://en3homeftp.net/release/images/oedrivers/openspa_bcmlinuxdvb_72604-${KV}-${SRCDATE}.tar.gz;name=openspa"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

do_compile() {
}

do_populate_sysroot() {
}

do_install() {
    install -d ${D}/lib/modules/${KV}/extra
    cp -Rf ${WORKDIR}/lib/modules/${KV}/extra/*.ko ${D}/lib/modules/${KV}/extra/
}

FILES_${PN} += "/lib/modules/${KV}/extra"
