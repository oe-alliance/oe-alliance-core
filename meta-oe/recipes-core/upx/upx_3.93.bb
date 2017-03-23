HOMEPAGE = "https://upx.github.io"
SUMMARY = "Ultimate executable compressor."

SRC_URI = " \
    http://github.com/upx/upx/releases/download/v${PV}/upx-${PV}-src.tar.xz \
    http://downloads.sourceforge.net/sevenzip/lzma465.tar.bz2;name=lzma;subdir=lzma-465 \
    file://fix_indentation_for_gcc6.patch \
    file://whitespace.patch \
    "

SRC_URI[md5sum] = "d8da85311492a5f9e3a1fecc979f879b"
SRC_URI[sha256sum] = "893f1cf1580c8f0048a4d328474cb81d1a9bf9844410d2fd99f518ca41141007"
SRC_URI[lzma.md5sum] = "29d5ffd03a5a3e51aef6a74e9eafb759"
SRC_URI[lzma.sha256sum] = "c935fd04dd8e0e8c688a3078f3675d699679a90be81c12686837e0880aa0fa1e"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=353753597aa110e0ded3508408c6374a"

DEPENDS = "zlib ucl-native"

S = "${WORKDIR}/upx-${PV}-src"

do_compile() {
    oe_runmake UPX_LZMA_VERSION=0x465 UPX_LZMADIR="${WORKDIR}/lzma-465" all
}

do_install_append() {
    install -d ${D}${bindir}
    install -m 755 ${B}/src/upx.out ${D}${bindir}/upx
}

BBCLASSEXTEND = "native"
