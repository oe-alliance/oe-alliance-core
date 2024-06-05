SUMMARY = "Wakelan sends a magic packet to wake up remote PC's"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=0636e73ff0215e8d672dc4c32c317bb3"

FILESEXTRAPATHS:prepend := "${THISDIR}/$PN}:"

SRC_URI = "http://www.ibiblio.org/pub/Linux/system/network/misc/${BPN}-${PV}.tar.gz \
            file://fix-headers-gcc14.patch"

inherit autotools-brokensep

do_install () {
        install -d ${D}${bindir}
        install -m 0755 ${S}/wakelan ${D}${bindir}/wakelan
}

FILES:${PN} = "${bindir}/wakelan"

INHIBIT_AUTO_STAGE = "1"

SRC_URI[md5sum] = "4a3a31d874967cd6ac761b7d4323e0d5"
SRC_URI[sha256sum] = "3df5eb8f877648799ab623cf1718ecc6f86eb0c2f51d344d8e860442dcc5cd6f"
