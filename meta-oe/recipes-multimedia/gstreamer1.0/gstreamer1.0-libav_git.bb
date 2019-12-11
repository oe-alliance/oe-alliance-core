SUMMARY = "Libav-based GStreamer 1.x plugin"
HOMEPAGE = "http://gstreamer.freedesktop.org/"
SECTION = "multimedia"
LICENSE = "GPLv2+ & LGPLv2+ & ( (GPLv2+ & LGPLv2.1+) | (GPLv3+ & LGPLv3+) )"
LICENSE_FLAGS = "commercial"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://COPYING.LIB;md5=6762ed442b3822387a51c92d928ead0d"

require gstreamer1.0-common.inc

DEPENDS = "bzip2 gstreamer1.0 gstreamer1.0-plugins-base xz zlib libbluray freetype libvorbis \
            alsa-lib libogg nasm-native libxml2 librtmp openssl x264 libtheora lame libvpx x265"

SRCREV_FORMAT = "gst_libav"

SRC_URI = "git://gitlab.freedesktop.org/gstreamer/gst-libav;protocol=https;branch=master;name=gst_libav \
            git://gitlab.freedesktop.org/gstreamer/meson-ports/ffmpeg;protocol=https;branch=meson-4.1.4;destsuffix=git/subprojects/FFmpeg;name=gst_ffmpeg \
            file://0001-meson-gst-ffmpeg-wrap.patch \
            file://0002-meson-build-optimization.patch \
            file://0003-configure-check-for-armv7ve-variant.patch \
            file://0004-workaround-to-build-gst-libav-for-i586-with-gcc.patch \
            file://0005-mips64-cpu-detection.patch \
"

SRC_URI_append_arm = " file://0006-rm-conflicting-pthreads-defs.patch"

inherit pkgconfig

EXTRA_OEMESON = "-Ddoc=disabled"

CFLAGS += "-Wno-implicit-function-declaration -Wno-stringop-overflow"

CFLAGS_remove_sh4 = "-Wno-stringop-overflow"
TARGET_CFLAGS_append_sh4 = " -std=gnu99"

FILES_${PN} += "${libdir}/gstreamer-1.0/*.so"
FILES_${PN}-dev += "${libdir}/gstreamer-1.0/*.la"
FILES_${PN}-staticdev += "${libdir}/gstreamer-1.0/*.a"

# http://errors.yoctoproject.org/Errors/Details/20493/
ARM_INSTRUCTION_SET_armv4 = "arm"
ARM_INSTRUCTION_SET_armv5 = "arm"
