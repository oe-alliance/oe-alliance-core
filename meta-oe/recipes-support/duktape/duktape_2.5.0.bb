DESCRIPTION = "Duktape is an embeddable Javascript engine, with a focus on portability and compact footprint."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=c83446610de1f63c7ca60cfcc82dec9d"

SRC_URI = "https://github.com/svaarala/duktape/releases/download/v${PV}/${BPN}-${PV}.tar.xz \
        file://makefile-unset-cc.patch"

SRC_URI[md5sum] = "e55fe3830f0d469dc67205b380515af0"
SRC_URI[sha256sum] = "83d411560a1cd36ea132bd81d8d9885efe9285c6bc6685c4b71e69a0c4329616"

do_compile() {
    oe_runmake -f Makefile.cmdline
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/duk ${D}${bindir}/
}

FILES_${PN} = "${bindir}"

INSANE_SKIP_${PN} += "ldflags"
