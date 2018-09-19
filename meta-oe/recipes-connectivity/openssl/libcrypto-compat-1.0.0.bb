SUMMARY = "Compatibility for packages that link to libcrypto or libssl 1.0.0"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} = "libcrypto libssl"
PV = "1.1"

RREPLACES_${PN} = "libcrypto1.0.0 libssl1.0.0"
RCONFLICTS_${PN} = "libcrypto1.0.0 libssl1.0.0"

do_install () {
    install -d ${D}${libdir}
    ln -sf libcrypto.so.1.0.2 ${D}${libdir}/libcrypto.so.1.0.0
    ln -sf libssl.so.1.0.2 ${D}${libdir}/libssl.so.1.0.0
}

FILES_${PN} = "${libdir}"

RPROVIDES_${PN} += "libcrypto1.0.0 libssl1.0.0"
