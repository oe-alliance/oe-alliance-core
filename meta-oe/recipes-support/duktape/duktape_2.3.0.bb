DESCRIPTION = "Duktape is an embeddable Javascript engine, with a focus on portability and compact footprint."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=7f60a8314f5ae7d0bed856bd375a4423"

SRC_URI = "https://github.com/svaarala/duktape/releases/download/v${PV}/${BPN}-${PV}.tar.xz \
        file://makefile-unset-cc.patch"

SRC_URI[md5sum] = "352105b39979fc766bbd0b3721e8c2b5"
SRC_URI[sha256sum] = "ed6e2f3ab2061628a7aeee27ccff16538ba6a151480cccf2e846bd061e45afe8"

do_compile() {
    oe_runmake -f Makefile.cmdline
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/duk ${D}${bindir}/
}

FILES_${PN} = "${bindir}"
