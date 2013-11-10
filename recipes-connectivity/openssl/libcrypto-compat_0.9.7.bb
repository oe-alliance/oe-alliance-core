DESCRIPTION = "Compatibility for packages that link to libcrypto or libssl 0.9.7"

require conf/license/license-gplv2.inc

AUTO_LIBNAME_PKGS = ""
PACKAGE_ARCH = "all"
DEPENDS = "openssl-old"
RDEPENDS_${PN} = "libcrypto0.9.8x libssl0.9.8x"
RPROVIDES_${PN} = "libcrypto${PV} libssl${PV}"
PR = "r5"
SRC_URI = ""
S = "${WORKDIR}"

do_configure () {
	true
}

do_compile () {
	true
}

do_install () {
	install -d ${D}/usr/lib
	ln -s /lib/libcrypto.so.0.9.8 ${D}/usr/lib/libcrypto.so.0.9.7
	ln -s libssl.so.0.9.8 ${D}/usr/lib/libssl.so.0.9.7
}

FILES_${PN} = "/usr/lib/"
