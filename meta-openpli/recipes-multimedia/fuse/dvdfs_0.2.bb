DESCRIPTION = "mounts a DVD using libdvdread"
LICENSE = "GPL"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

HOMEPAGE = "http://www.jspenguin.org/software/dvdfs/"
DEPENDS = "fuse libdvdread"
PR = "r1"

SRC_URI = "http://www.jspenguin.org/software/${PN}/${PN}-${PV}.tar.gz \
	file://crosscompile.patch \
	file://defaultdevicesr0.patch \
	"


do_install() {
	install -d ${D}/usr/bin
	install -m 755 ${S}/${PN} ${D}/usr/bin/${PN}
}

SRC_URI[md5sum] = "dc771ef0114fcad783df180f1ebfa66b"
SRC_URI[sha256sum] = "0db312b8f5f9caca6c3f2a57a9a7b1d77c4c509d8d84e081c7a53d03e7d815d0"
