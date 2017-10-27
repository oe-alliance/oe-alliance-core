SUMMARY = "Compatibility for packages that link to libcrypto or libssl 0.9.7"

require conf/license/license-gplv2.inc

inherit allarch
RDEPENDS_${PN} = "libcrypto libssl"
PV = "1.0"

RREPLACES_${PN} = "libcrypto-compat"
RCONFLICTS_${PN} = "libcrypto-compat"

do_install () {
    install -d ${D}/usr/lib
    ln -s libcrypto.so.1.0.0 ${D}/usr/lib/libcrypto.so.0.9.7
    ln -s libssl.so.1.0.0 ${D}/usr/lib/libssl.so.0.9.7
}

FILES_${PN} = "/usr/lib"
