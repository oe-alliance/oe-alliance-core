DESCRIPTION = "Plugin for libvupl Demo"
SECTION = "devel"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PR = "r1"

RDEPENDS_${PN} = "libvupl-example-cube"

SRC_URI = "http://archive.vuplus.com/download/build_support/enigma2-plugin-demoplugins-libvupldemo.${PR}.tar.gz"

do_install_append() {
	install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/libvupldemo
	install -m 0644 ${WORKDIR}/enigma2-plugin-demoplugins-libvupldemo/* ${D}/usr/lib/enigma2/python/Plugins/Extensions/libvupldemo/
}

do_package_qa() {
}

FILES_${PN} = "/"

SRC_URI[md5sum] = "5f3a250fd3d2e9bde34f4e3fea265006"
SRC_URI[sha256sum] = "160e98a21cc161fac7d977000e4b2eb350993ea8abeadca5824ecfd34962f0fe"
