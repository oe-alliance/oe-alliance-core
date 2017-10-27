DESCRIPTION = "dcadec is a free DTS Coherent Acoustics decoder with support for HD extensions."
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING.LGPLv2.1;md5=4fbd65380cdd255951079008b364516c"

inherit autotools pkgconfig

SRC_URI = "git://github.com/foo86/dcadec.git;protocol=http"
SRCREV = "0e074384c9569e921f8facfe3863912cdb400596"

S = "${WORKDIR}/git"

do_compile() {
	PREFIX=/usr make -C ${S}
}

do_install() {
	mkdir -p ${D}/usr/include
	mkdir -p ${D}/usr/lib/pkgconfig
	PREFIX=/usr DESTDIR=${D} make -C ${S} install
}

do_package_qa() {
}

FILES_${PN} = "/"