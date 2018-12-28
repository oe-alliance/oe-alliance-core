HOMEPAGE = "https://upx.github.io"
SUMMARY = "Ultimate executable compressor."
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=353753597aa110e0ded3508408c6374a"

DEPENDS = "zlib ucl-native"

inherit gitpkgv perlnative

SRCREV = "${AUTOREV}"
PKGV = "3.95+git${GITPKGV}"
PV = "3.95+git${SRCPV}"

SRC_URI = "gitsm://github.com/upx/upx.git;protocol=http"

S = "${WORKDIR}/git"

do_compile() {
    oe_runmake all
}

do_install_append() {
    install -d ${D}${bindir}
    install -m 755 ${B}/src/upx.out ${D}${bindir}/upx
}

BBCLASSEXTEND = "native"
