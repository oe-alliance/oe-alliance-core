SUMMARY = "Tool to optimize PNG images"
LICENSE = "Proprietary"
SECTION = "console/graphics"
HOMEPAGE = "http://pmt.sourceforge.net/pngcrush"

LIC_FILES_CHKSUM = "file://pngcrush.c;beginline=17;md5=bcb0769acd253e9682a27a0b0f28ce1f"

SRC_URI = "https://sourceforge.net/projects/pmt/files/pngcrush/1.8.13/pngcrush-1.8.13.tar.gz;name=src \
        file://dont-guess-ptrdiff_t.patch"

SRC_URI[src.md5sum] = "0c4dad7893a4014d8bb28b24a0dd33e6"
SRC_URI[src.sha256sum] = "bac37d4b2be88d7e88aadcde9661beb3a513a90e7d26784f906c1e2da8ba332e"

EXTRA_OEMAKE = "CC='${CC}' CFLAGS='${CFLAGS}' LD='${CC} ${LDFLAGS}'"

do_install () {
        install -d ${D}${bindir}
        install -m 755 ${BPN} ${D}${bindir}
}

BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"
