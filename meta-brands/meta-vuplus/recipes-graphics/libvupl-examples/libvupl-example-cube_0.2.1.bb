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

SRC_URI[md5sum] = "a904074c39b7d37293e1ff2cfe990b77"
SRC_URI[sha256sum] = "eeda739794d85a79e0beaafd836136367ff50d85a7adf8bba5b40874c3377c9f"
