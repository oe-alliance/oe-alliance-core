SUMMARY = "replex remultiplexes TS (DVB) to PS (DVD)"
SECTION = "console/multimedia"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=8ca43cbc842c2336e835926c2166c28b"
SRC_URI = "http://ftp.arm.slackware.com/slacky/slackware-12.0/utilities/${BPN}/${PV}/src/${BPN}-${PV}.tar.gz \
    file://fix-makefile.patch \
"
PR = "r1"

SRC_URI[md5sum] = "de02c1b55bc7863f811adffe611f1c71"
SRC_URI[sha256sum] = "68718d6671570815e8576b6e0b595195f5cea435194209ee5528721cb8737b63"


CFLAGS = '-D_LARGEFILE_SOURCE -D_FILE_OFFSET_BITS=64 -D_LARGEFILE64_SOURCE -DVERSION=\\"${PV}\\"'

S = "${WORKDIR}/${BPN}-${PV}"

do_install() {
  install -d 0755 ${D}/${bindir}
  install -m 0755 ${S}/replex ${D}/${bindir}
}

FILES_${PN} = "${bindir}/replex"