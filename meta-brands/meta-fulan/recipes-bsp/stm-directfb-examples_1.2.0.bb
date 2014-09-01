inherit src_rpm

DESCRIPTION = "The DirectFB-examples package contains a set of simple DirectFB \
      applications that can be used to test and demonstrate various DirectFB \
      features"
DEPENDS = "directfb"
SECTION = "libs"
LICENSE = "MIT"
PR = "r0"

RPROVIDES_${PN} = "directfb-examples"

STLINUX_SH_UPD_SRPMS = "http://ftp.stlinux.com/pub/stlinux/2.4/updates/SRPMS"

SRC_URI = "${STLINUX_SH_UPD_SRPMS}/stlinux24-target-directfb-examples-1.2.0+git1.76b8894-5.src.rpm"
LOCAL_SRC = "\
            file://${WORKDIR}/DirectFB-examples-1.2.0-0001-df_bltload-support-stmfb-s-API-for-retrieving-blitte.patch;patch=1;pnum=1 \
            file://${WORKDIR}/DirectFB-examples-1.2.0-0002-df_dok-some-local-changes-STM-applies.patch;patch=1;pnum=1 \
            file://${WORKDIR}/DirectFB-examples-1.2.0-0003-df_dok-limit-stretch-blit-tests-to-64-rescales.patch;patch=1;pnum=1 \
            file://${WORKDIR}/DirectFB-examples-1.2.0-0004-df_particle-use-triple-buffering-if-possible.patch;patch=1;pnum=1 \
            file://${WORKDIR}/DirectFB-examples-1.2.0-0005-df_particle-printf-frames-s.patch;patch=1;pnum=1 \
            file://${WORKDIR}/DirectFB-examples-1.2.0+git1.76b8894.tar.bz2 \
"


LIC_FILES_CHKSUM = "file://COPYING;md5=ecf6fd2b19915afc4da56043926ca18f"

S = "${WORKDIR}/DirectFB-examples-${PV}+git1.76b8894"

inherit autotools

SRC_URI[md5sum] = "ffaf456781659d479e97a0cc3c6edbfa"
SRC_URI[sha256sum] = "50eebf8522e77ecd500746731862043729f54aaad1f849a3ebd1fb2efa6dd43a"
