SUMMARY = "Compatibility for packages that link to libcrypto or libssl 0.9.7"

require conf/license/license-gplv2.inc

PACKAGE_ARCH = "all"
RDEPENDS_${PN} = "libcrypto libssl"
PR = "r8"

do_install () {
    install -d ${D}/lib ${D}/usr/lib
    ln -s libcrypto.so.1.0.0 ${D}/lib/libcrypto.so.${PV}
    install -d ${D}/usr/lib
    ln -s libssl.so.1.0.0 ${D}/usr/lib/libssl.so.${PV}
}

FILES_${PN} = "/usr/lib /lib/"
