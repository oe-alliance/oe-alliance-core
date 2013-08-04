DESCRIPTION = "Compatibility for packages that link to libcrypto or libssl 0.9.7"

require conf/license/license-gplv2.inc

PACKAGE_ARCH = "all"
RDEPENDS = "libcrypto libssl"
PR = "r6"

do_install () {
	install -d ${D}/lib
	ln -s libcrypto.so.0.9.8 ${D}/lib/libcrypto.so.${PV}
	install -d ${D}/usr/lib
	ln -s libssl.so.0.9.8 ${D}/usr/lib/libssl.so.${PV}
}

FILES_${PN} = "/usr/lib/ /lib/"
