SUMMARY = "Yet Another Flash File System"
DESCRIPTION = "Tools for managing 'yaffs' file systems for bootmenu."
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = " "

inherit pkgconfig

SRC_URI = "file://mkyaffs2utils.zip"

S = "${UNPACKDIR}"

do_compile() {
    oe_runmake -C mkyaffs2image \
        CC="${CC}" \
        CFLAGS="${CFLAGS}" \
        LDFLAGS="${LDFLAGS}"
}

do_compile:class-native() {
    oe_runmake -C mkyaffs2image \
        CC="${BUILD_CC}" \
        CFLAGS="${BUILD_CFLAGS}" \
        LDFLAGS="${BUILD_LDFLAGS}"
}

FILES:${PN} += "${bindir}"

do_install() {
	install -d ${D}${bindir}
	install -m 0755 mkyaffs2image/mkyaffs ${D}${bindir}
}

BBCLASSEXTEND = "native"
