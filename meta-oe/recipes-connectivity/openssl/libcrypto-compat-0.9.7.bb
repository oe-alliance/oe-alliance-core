SUMMARY = "Compatibility for packages that link to libcrypto or libssl 0.9.7"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} = "libcrypto libssl"
PV = "1.0"

RREPLACES_${PN} = "libcrypto-compat"
RCONFLICTS_${PN} = "libcrypto-compat"

do_install () {
    install -d ${D}${libdir}
    ln -sf libcrypto.so.1.0.2 ${D}${libdir}/libcrypto.so.0.9.7
    ln -sf libssl.so.1.0.2 ${D}${libdir}/libssl.so.0.9.7
}

FILES_${PN} = "${libdir}"

# Set these explicitly
#
RPROVIDES_${PN} += "libcrypto.so.0.9.7 libssl.so.0.9.7"
