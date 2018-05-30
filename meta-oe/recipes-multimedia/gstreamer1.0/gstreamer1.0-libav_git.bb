DEFAULT_PREFERENCE = "-1"

include gstreamer1.0-libav.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://COPYING.LIB;md5=6762ed442b3822387a51c92d928ead0d \
                    file://ext/libav/gstav.h;beginline=1;endline=18;md5=a752c35267d8276fd9ca3db6994fca9c \
                    file://gst-libs/ext/libav/LICENSE.md;md5=e6c05704638b696e6df04470d7fede29 \
                    file://gst-libs/ext/libav/COPYING.GPLv2;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://gst-libs/ext/libav/COPYING.GPLv3;md5=d32239bcb673463ab874e80d47fae504 \
                    file://gst-libs/ext/libav/COPYING.LGPLv2.1;md5=bd7a443320af8c812e4c18d1b79df004 \
                    file://gst-libs/ext/libav/COPYING.LGPLv3;md5=e6a600fd5e1d9cbde2d983680233ad02"

# To build using the system libav/ffmpeg, append "libav" to PACKAGECONFIG
# and remove the ffmpeg sources from SRC_URI below. However, first note the
# warnings in gstreamer1.0-libav.inc
SRC_URI = " \
    git://anongit.freedesktop.org/gstreamer/gst-libav;name=base \
    git://anongit.freedesktop.org/gstreamer/common;destsuffix=git/common;name=common \
    git://github.com/FFmpeg/FFmpeg;destsuffix=git/gst-libs/ext/libav;name=ffmpeg;branch=release/3.4 \
    file://0001-Disable-yasm-for-libav-when-disable-yasm.patch \
    file://workaround-to-build-gst-libav-for-i586-with-gcc.patch \
    file://mips64_cpu_detection.patch \
    file://0001-configure-check-for-armv7ve-variant.patch \
 "

GST_VERSION_FULL ="1.14.1"
inherit gitpkgv
PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

UPSTREAM_CHECK_GITTAGREGEX = "(?P<pver>(\d+(\.\d+)+))"

SRCREV_base = "04b3867cb8968f6c5122258041778ebb9dfa400f"
SRCREV_common = "46d420818ed8172022caf4503894076b18b1e153"
SRCREV_ffmpeg = "a877ab75eb8faa2de33c9118053f44b0d4548f09"
SRCREV_FORMAT = "base"

LIBAV_INCLUDED_EXLUCED = "--disable-everything \
						--enable-decoder=wmalossless --enable-decoder=wmapro --enable-decoder=wmav1 --enable-decoder=wmav2 --enable-decoder=wmavoice \
						--enable-decoder=truehd --enable-decoder=dca --enable-decoder=mlp"


SRC_URI_append_sh4 = " \
    file://libav-fix-sh4-compile-gcc48.patch \
"

LIBAV_EXTRA_CONFIGURE_COMMON_ARG = "--target-os=linux \
	--cc='${CC}' --as='${CC}' --ld='${CC}' --nm='${NM}' --ar='${AR}' \
	--ranlib='${RANLIB}' \
	${GSTREAMER_1_0_DEBUG} \
	${@bb.utils.contains('TARGET_FPU', 'soft', '--disable-mipsfpu', '--enable-mipsfpu', d)} \
	--disable-mipsdsp \
	--disable-mipsdspr2 \
	${LIBAV_INCLUDED_EXLUCED} \
	--cross-prefix='${HOST_PREFIX}'"

S = "${WORKDIR}/git"

do_configure_prepend() {
	cd ${S}
	./autogen.sh --noconfigure
	cd ${B}
}
