SUMMARY = "Compatibility for packages that link to hardfloat ld-linux lib to emulate softfloat one"

require conf/license/license-gplv2.inc

inherit allarch
RDEPENDS_${PN} = "glibc"

RREPLACES_${PN} = "libcrypto0.9.8 libssl0.9.8"
RCONFLICTS_${PN} = "libcrypto0.9.8 libssl0.9.8"

do_install () {
    install -d ${D}/lib
    ln -sf ld-${PV}.so ${D}/lib/ld-linux.so.3
}

FILES_${PN} = "/lib"

do_populate_sysroot[noexec] = "1"
