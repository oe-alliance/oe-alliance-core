SUMMARY = "Mupen64Plus-Next libretro core"
DESCRIPTION = "Nintendo 64 emulator core with ARM and AArch64 dynamic recompilers."
HOMEPAGE = "https://github.com/libretro/mupen64plus-libretro-nx"

LICENSE = "GPL-2.0-only"
PR = "r1"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "git://github.com/libretro/mupen64plus-libretro-nx.git;protocol=https;branch=develop"
SRCREV = "12edd2c74a517ff86dfa8cfc71ad75e4c10486d5"
PV = "0.1+git20261008.${SRCPV}"

require libretro-core.inc

LIBRETRO_CORE_PATH = "${S}/mupen64plus_next_libretro.so"

MUPEN_TARGET_ARCH = "${@'aarch64' if d.getVar('TARGET_ARCH') == 'aarch64' else 'arm'}"
MUPEN_HAVE_NEON = "${@'0' if d.getVar('TARGET_ARCH') == 'aarch64' else '1'}"
MUPEN_GRAPHICS ??= "FORCE_GLES=1"

DEPENDS = "virtual/libgles2 zlib"
RDEPENDS:${PN} += "virtual-libgles2 virtual-egl"

do_compile() {
    oe_runmake \
        platform=unix \
        ARCH=${MUPEN_TARGET_ARCH} \
        WITH_DYNAREC=${MUPEN_TARGET_ARCH} \
        HAVE_NEON=${MUPEN_HAVE_NEON} \
        ${MUPEN_GRAPHICS}
}
