SUMMARY = "H.264/MPEG-4 AVC video encoder"
DESCRIPTION = "A free software library and application for encoding video streams into the H.264/MPEG-4 AVC format."
HOMEPAGE = "http://www.videolan.org/developers/x264.html"
LICENSE = "GPL-2.0-only"
LICENSE_FLAGS = "commercial"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

DEPENDS = "nasm-native"

SRCREV = "b093bbe7d9bc642c8f24067cbdcc73bb43562eab"

PV = "r2970+git"

SRC_URI = "git://code.videolan.org/videolan/x264;protocol=https;branch=master \
           file://don-t-default-to-cortex-a9-with-neon.patch \
           file://Fix-X32-build-by-disabling-asm.patch \
           "

UPSTREAM_CHECK_COMMITS = "1"

S = "${WORKDIR}/git"

inherit lib_package pkgconfig perlnative

X264_DISABLE_ASM = "--disable-asm"
X264_DISABLE_ASM:aarch64 = ""
X264_DISABLE_ASM:armv4 = "--disable-asm"
X264_DISABLE_ASM:armv5 = "--disable-asm"
X264_DISABLE_ASM:powerpc = "${@bb.utils.contains("TUNE_FEATURES", "spe", "--disable-asm", "", d)}"
X264_DISABLE_ASM:mipsarch = "${@bb.utils.contains("TUNE_FEATURES", "r6", "", "--disable-asm", d)}"

EXTRA_OECONF = '--prefix=${prefix} \
                --host=${HOST_SYS} \
                --libdir=${libdir} \
                --cross-prefix=${TARGET_PREFIX} \
                --sysroot=${STAGING_DIR_TARGET} \
                --enable-shared \
                --enable-static \
                --disable-lavf \
                --disable-swscale \
                --disable-opencl \
                --enable-pic \
                ${X264_DISABLE_ASM} \
               '

do_configure() {
    install -m 0755 ${STAGING_DATADIR_NATIVE}/gnu-config/config.guess ${S}
    install -m 0755 ${STAGING_DATADIR_NATIVE}/gnu-config/config.sub ${S}
    ./configure ${EXTRA_OECONF}
}

do_install() {
    oe_runmake install DESTDIR=${D}
}

AS[unexport] = "1"
