SUMMARY = "Simple tool for input event debugging."
HOMEPAGE = "http://people.freedesktop.org/~whot/evtest/"
AUTHOR = "Vojtech Pavlik <vojtech@suse.cz>"
SECTION = "console/utils"
#PRIORITY = "optional"
LICENSE = "GPLv2"
PR = "r4"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"


DEPENDS = "libxml2"

SRC_URI = "http://cgit.freedesktop.org/~whot/evtest/snapshot/evtest-${PV}.tar.bz2;name=archive \
           file://fix-xml-issue.patch \
"

inherit autotools-brokensep

do_install () {
        install -d ${D}${bindir}
        install -m 0755 ${WORKDIR}/${PN}-${PV}/evtest ${D}${bindir}/evtest
}

FILES_${PN} = "${bindir}/evtest"

INHIBIT_AUTO_STAGE = "1"

SRC_URI[archive.md5sum] = "0ef3fe5e20fa2dee8994827d48482902"
SRC_URI[archive.sha256sum] = "6e93ef54f0aa7d263f5486ce4a14cac53cf50036bfd20cf045fef2b27ee6664b"
