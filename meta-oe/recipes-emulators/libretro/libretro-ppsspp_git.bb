SUMMARY = "PPSSPP libretro core"
DESCRIPTION = "Experimental Sony PlayStation Portable emulator core."
HOMEPAGE = "https://github.com/hrydgard/ppsspp"

LICENSE = "GPL-2.0-or-later"
PR = "r3"
LIC_FILES_CHKSUM = "file://LICENSE.TXT;md5=e336f8162cddec7981e240f46825d8a2"

SRC_URI = "gitsm://github.com/hrydgard/ppsspp.git;protocol=https;branch=master \
           file://0001-libretro-armv7-dynarec.patch \
           file://0002-libretro-gles2-clip-distance-enums.patch \
           file://0003-libretro-adrenotools-android-only.patch \
           file://0004-libretro-libpng-neon-aarch64.patch \
"
SRCREV = "c8814f545f32a262884e427616281a286c697b32"
PV = "0.1+git20261008.${SRCPV}"

require libretro-core.inc

DEPENDS = "virtual/egl virtual/libgles2 zlib"
RDEPENDS:${PN} += "virtual-libgles2 virtual-egl"

LIBRETRO_CORE_FILE = "ppsspp_libretro.so"
LIBRETRO_CORE_PATH = "${S}/libretro/${LIBRETRO_CORE_FILE}"
PPSSPP_PLATFORM = "${@'arm64-gles' if d.getVar('TARGET_ARCH') == 'aarch64' else 'armv7-gles-neon-hardfloat'}"
PPSSPP_TARGET_ARCH = "${@'arm64' if d.getVar('TARGET_ARCH') == 'aarch64' else 'armv7'}"

do_compile() {
    oe_runmake -C ${S}/libretro \
        platform=${PPSSPP_PLATFORM} \
        TARGET_ARCH=${PPSSPP_TARGET_ARCH} \
        AS="${CC}" \
        GLEW_EGL=1
}

do_install() {
    install -d ${D}${libdir}/libretro
    install -m 0755 ${LIBRETRO_CORE_PATH} ${D}${libdir}/libretro/${LIBRETRO_CORE_FILE}

    # The core expects the upstream assets below the writable RetroArch
    # system directory. The launcher links this read-only packaged copy into
    # that directory; firmware and game data remain user supplied.
    install -d ${D}${datadir}/libretro/PPSSPP
    cp -R --no-preserve=ownership ${S}/assets/. ${D}${datadir}/libretro/PPSSPP/
}

FILES:${PN} += "${datadir}/libretro/PPSSPP"
