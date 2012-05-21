DESCRIPTION = "Tool to optimize PNG images"
LICENSE = "Proprietary"
SECTION = "console/graphics"
HOMEPAGE = "http://pmt.sourceforge.net/pngcrush"

LIC_FILES_CHKSUM = "file://pngcrush.c;beginline=66;endline=107;md5=61856b3db001e662b4a4fd8b376e3e69"

PR = "r1"

SRC_URI = "${SOURCEFORGE_MIRROR}/pmt/pngcrush-${PV}.tar.gz;name=src"

SRC_URI[src.md5sum] = "3dcf459201254300e463c2f29a304978"
SRC_URI[src.sha256sum] = "291cad915ed4643996815fef2c08c90e30471cf3937b8bf8f9d969f7a06b8e20"

#DEPENDS += "libsdl-net smpeg"

EXTRA_OEMAKE = "CC='${CC}' CFLAGS='${CFLAGS}' LD='${CC} ${LDFLAGS}'"

do_install () {
        install -d ${D}${bindir}
        install -m 755 ${BPN} ${D}${bindir}
}

BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"
