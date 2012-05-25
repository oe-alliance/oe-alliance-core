DESCRIPTION = "Compatibility for packages that link to libcrypto or libssl 0.9.7"

require conf/license/openpli-gplv2.inc

PACKAGES = "${PN}"
PACKAGE_ARCH = "all"
RDEPENDS_${PN} = "libcrypto"
RPROVIDES_${PN} = "libcrypto${PV} libssl${PV}"
PR = "r2"
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
