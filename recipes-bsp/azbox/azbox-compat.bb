DESCRIPTION = "Azbox compatibility links"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

PV = "1.0"
PR = "r2"

inherit allarch

do_install() {
	install -d ${D}/lib
	ln -sf libgcc_s.so.1 ${D}/lib/libgcc_s_nof.so.1
	ln -sf libcrypto.so.0.9.8 ${D}/lib/libcrypto.so.0.9.7
	install -d ${D}/usr/lib
	ln -sf libjpeg.so.8 ${D}${libdir}/libjpeg.so.62
	ln -sf libssl.so.0.9.8 ${D}/usr/lib/libssl.so.0.9.7
	ln -sf libpython2.7.so.1.0 ${D}/usr/lib/libpython2.6.so.1.0
}

PACKAGES = "${PN}"
