SUMMARY = "Compatibility for packages that link to libcrypto or libssl 1.0.0"

require conf/license/license-gplv2.inc

inherit allarch
RDEPENDS_${PN} = "libcrypto libssl"
PV = "1.1"

RREPLACES_${PN} = "libcrypto1.0.0 libssl1.0.0"
RCONFLICTS_${PN} = "libcrypto1.0.0 libssl1.0.0"

do_install () {
    install -d ${D}/usr/lib
    ln -sf libcrypto.so.1.0.2 ${D}/usr/lib/libcrypto.so.1.0.0
    ln -sf libssl.so.1.0.2 ${D}/usr/lib/libssl.so.1.0.0
}

FILES_${PN} = "/usr/lib"

RPROVIDES_${PN} += "libcrypto1.0.0 libssl1.0.0"
