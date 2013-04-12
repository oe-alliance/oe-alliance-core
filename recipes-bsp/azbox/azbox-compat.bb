DESCRIPTION = "Azbox compatibility links"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

PV = "1.0"
PR = "r0"

FILES = "/lib/libgcc_s_nof.so.1 /usr/lib/libcrypto.so.0.9.7 /usr/lib/libssl.so.0.9.7"
PACKAGE_ARCH = "all"

do_install() {
	install -d ${D}/lib
	ln -sf libgcc_s.so.1 ${D}/lib/libgcc_s_nof.so.1
	install -d ${D}/usr/lib
	ln -sf libcrypto.so.0.9.8 ${D}/usr/lib/libcrypto.so.0.9.7
	ln -sf libssl.so.0.9.8 ${D}/usr/lib/libssl.so.0.9.7
	ln -sf libpython2.6.so.1.0 ${D}/usr/lib/libpython2.5.so.1.0
}
