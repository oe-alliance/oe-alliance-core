DESCRIPTION = "Cube Demo example for libvupl"
SECTION = "devel"
HOMEPAGE = "https://code.google.com/archive/p/opengles-book-samples/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7c01ce978e205f99897d26c061b2c09a"

PR = "r1"

DEPENDS = "libvupl libgles"
RDEPENDS_${PN} = "libvupl libgles"

SRC_URI = "http://archive.vuplus.com/download/build_support/${PN}-${PV}.${PR}.tar.gz"

S = "${WORKDIR}/${PN}-${PV}"

EXTRA_OECMAKE += "-DVUPLUS=1"

inherit cmake

do_install() {
	install -d ${D}/usr/bin
	install -m 0755 ${WORKDIR}/build/Simple_VertexShader/Simple_VertexShader ${D}/usr/bin/cube
}

do_package_qa() {
}

SRC_URI[md5sum] = "fe812abfc9c52ed26c4aa7192f94fe03"
SRC_URI[sha256sum] = "0cc58ec3d6d0d1be358e805268fa29704dde6fceeace8bc60d001bb63dac26ee"
