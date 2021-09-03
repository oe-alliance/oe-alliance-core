SUMMARY = "Yet Another Flash File System"
DESCRIPTION = "Tools for managing 'yaffs' file systems for bootmenu."

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = " "
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit pkgconfig

PV = "1.0.0"

SRC_URI = "file://mkyaffs2utils.tar.bz2"

#INHIBIT_PACKAGE_STRIP = "1"
#INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

S = "${WORKDIR}"

do_compile() {
    cd mkyaffs2image && oe_runmake
}

FILES_${PN} += "${bindir}"

do_install() {
	install -d ${D}${bindir}
	install -m 0755 mkyaffs2image/mkyaffs ${D}${bindir}
}

do_package_qa() {
}

BBCLASSEXTEND = "native"
