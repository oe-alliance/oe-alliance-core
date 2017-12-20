SUMMARY = "Compatibility for packages that link to libcrypto or libssl 1.0.0"

require conf/license/license-gplv2.inc

inherit allarch
RDEPENDS_${PN} = "libcrypto libssl"
PV = "1.0"

RREPLACES_${PN} = "libcrypto0.9.8 libssl0.9.8"
RCONFLICTS_${PN} = "libcrypto0.9.8 libssl0.9.8"

do_install () {
    install -d ${D}/usr/lib
    ln -s libcrypto.so.1.0.2 ${D}/usr/lib/libcrypto.so.1.0.0
    ln -s libssl.so.1.0.2 ${D}/usr/lib/libssl.so.1.0.0
}

FILES_${PN} = "/usr/lib"
