SUMMARY = "Compatibility for packages that link to libcrypto or libssl 0.9.8"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} = "libcrypto libssl"
PV = "1.0"

RREPLACES_${PN} = "libcrypto0.9.8 libssl0.9.8"
RCONFLICTS_${PN} = "libcrypto0.9.8 libssl0.9.8"

do_install () {
    install -d ${D}${libdir}
    ln -sf libcrypto.so.1.0.2 ${D}${libdir}/libcrypto.so.0.9.8
    ln -sf libssl.so.1.0.2 ${D}${libdir}/libssl.so.0.9.8
}

FILES_${PN} = "${libdir}"
