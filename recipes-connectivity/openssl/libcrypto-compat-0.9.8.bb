SUMMARY = "Compatibility for packages that link to libcrypto or libssl 0.9.8"

require conf/license/license-gplv2.inc

PACKAGE_ARCH = "all"
RDEPENDS_${PN} = "libcrypto libssl"
PV = "1.0"
PR = "r0"

do_install () {
    install -d ${D}/lib ${D}/usr/lib
    ln -s libcrypto.so.1.0.0 ${D}/lib/libcrypto.so.0.9.8
    install -d ${D}/usr/lib
    ln -s libssl.so.1.0.0 ${D}/usr/lib/libssl.so.0.9.8
}

FILES_${PN} = "/usr/lib /lib/"
