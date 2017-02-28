SUMMARY = "decoding library for DTS Coherent Acoustics streams"
SECTION = "libs/multimedia"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"
SRCREV = "4f6b5d6e6fb35994c342de95bf05e20117da549b"
PR = "r5"

SRC_URI = "git://anonscm.debian.org/collab-maint/${PN}.git;protocol=git"

S = "${WORKDIR}/git"

inherit autotools-brokensep lib_package pkgconfig

do_unpackpost() {
    cd ${S}
    QUILT_PATCHES=${S}/debian/patches quilt push -a
    # single precision is enough and speeds up libdca by about 10-15%
    sed -i -e 's/double/sample_t/g' ${S}/libdca/*.c ${S}/libdca/*.h
}

addtask unpackpost after do_patch before do_configure
