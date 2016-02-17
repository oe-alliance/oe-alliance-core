include gstreamer1.0-libav.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://COPYING.LIB;md5=6762ed442b3822387a51c92d928ead0d \
                    file://ext/libav/gstav.h;beginline=1;endline=18;md5=a752c35267d8276fd9ca3db6994fca9c \
                    file://gst-libs/ext/libav/LICENSE.md;md5=5c6d1ed56d15ca87ddec48d0c3a2051d \
                    file://gst-libs/ext/libav/COPYING.GPLv2;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://gst-libs/ext/libav/COPYING.GPLv3;md5=d32239bcb673463ab874e80d47fae504 \
                    file://gst-libs/ext/libav/COPYING.LGPLv2.1;md5=bd7a443320af8c812e4c18d1b79df004 \
                    file://gst-libs/ext/libav/COPYING.LGPLv3;md5=e6a600fd5e1d9cbde2d983680233ad02"

SRC_URI = "git://anongit.freedesktop.org/gstreamer/gst-libav;branch=master \
	file://0001-Disable-yasm-for-libav-when-disable-yasm.patch \
"

S = "${WORKDIR}/git"
#SRCREV = "${AUTOREV}"
SRCREV = "da335a0ea368d2f71e2824adc3e027332db8a58c"
inherit gitpkgv
#PV = "1.7+git${SRCPV}"
#PKGV = "1.7+git${GITPKGV}"

SRC_URI_append_sh4 = " \
    file://libav-fix-sh4-compile-gcc48.patch \
"


LIBAV_EXTRA_CONFIGURE_COMMON_ARG = "--target-os=linux \
  --cc='${CC}' --as='${CC}' --ld='${CC}' --nm='${NM}' --ar='${AR}' \
  --ranlib='${RANLIB}' \
  ${@base_contains('TARGET_FPU', 'soft', '--disable-mipsfpu', '--enable-mipsfpu', d)} \
  --disable-mipsdspr1 \
  --disable-mipsdspr2 \
  ${GSTREAMER_1_0_DEBUG} \
  --cross-prefix='${HOST_PREFIX}'"

do_configure_prepend() {
	cd ${S}
	./autogen.sh --noconfigure
	cd ${B}
}

