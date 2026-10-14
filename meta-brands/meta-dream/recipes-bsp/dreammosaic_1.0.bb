SUMMARY = "Mosaic decoders for the DreamOne/DreamTwo"
DESCRIPTION = "Adds the video decoders video2..video5 and the mosaic decoder mode (/proc/stb/video/decodermode) for a picture of up to four services."
require conf/license/license-gplv2.inc

COMPATIBLE_MACHINE = "^(dreamone|dreamtwo)$"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit module

# the port struct of the media modules the driver release is built from
SRC_URI = " \
    file://Makefile \
    file://dreammosaic.c \
    file://streambuf.h \
    https://source.mynonpublic.com/dreambox/dreambox-dvb-modules_4.9-20230404r0_meson64.tar.xz;name=meson64;subdir=dmm \
"

SRC_URI[meson64.md5sum] = "180270b3c9780b07aa3df139086da018"
SRC_URI[meson64.sha256sum] = "f60ed1693d1af7e876249ec9dbfa670bf76d2b00dabf9619748ecf5935f9cd65"

S = "${UNPACKDIR}"

RDEPENDS:${PN} += "dreambox-dvb-modules-meson64"
KERNEL_MODULE_AUTOLOAD += "dreammosaic"

# The driver release has no Module.symvers: take the symbol versions of
# the decoder and clock exports from the modules. They count as vmlinux
# symbols, the modules are not packaged as kernel-module-* (and load
# before this one).
do_compile:prepend() {
    for ko in $(find ${S}/dmm -name decoder_common.ko -o -name media_clock.ko); do
        ${NM} $ko | sed -n 's/^\(0*\)\([0-9a-f]\{1,8\}\) A __crc_\(.*\)$/0x\2\t\3\tvmlinux\tEXPORT_SYMBOL/p'
    done > ${S}/dmm.symvers
    test -s ${S}/dmm.symvers
}
