HOMEPAGE = "https://upx.github.io"
SUMMARY = "Ultimate executable compressor."

SRC_URI = " \
    http://github.com/upx/upx/releases/download/v${PV}/upx-${PV}-src.tar.xz \
    http://downloads.sourceforge.net/sevenzip/lzma465.tar.bz2;name=lzma;subdir=lzma-465 \
    file://fix_indentation_for_gcc6.patch \
    file://whitespace.patch \
    file://void.patch \
    "

SRC_URI[md5sum] = "19e898edc41bde3f21e997d237156731"
SRC_URI[sha256sum] = "81ef72cdac7d8ccda66c2c1ab14f4cd54225e9e7b10cd40dd54be348dbf25621"
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
