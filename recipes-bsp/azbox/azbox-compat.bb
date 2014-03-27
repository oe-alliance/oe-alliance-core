SUMMARY = "Azbox compatibility links"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

PV = "1.0"
PR = "r4"

inherit allarch

do_install() {
    install -d ${D}/lib
    ln -sf libgcc_s.so.1 ${D}/lib/libgcc_s_nof.so.1
    install -d ${D}/usr/lib
    ln -sf libjpeg.so.8 ${D}${libdir}/libjpeg.so.62
    ln -sf libpython2.7.so.1.0 ${D}/usr/lib/libpython2.6.so.1.0
}

PACKAGES = "${PN}"
