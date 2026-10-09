SUMMARY = "Snes9x libretro core"
DESCRIPTION = "Current stable Snes9x core for Super Nintendo and Super Famicom emulation through RetroArch."
HOMEPAGE = "https://github.com/snes9xgit/snes9x"

LICENSE = "LicenseRef-Snes9x"
LIC_FILES_CHKSUM = "file://LICENSE;md5=6691f801143624b98c1f074d27d27ba7"

SRC_URI = "git://github.com/snes9xgit/snes9x.git;protocol=https;branch=master"
SRCREV = "921f9f7b83660eb44ad263022a57a4a029057c37"
PV = "1.63"
PR = "r1"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming', '.*', '^$', d)}"

SNES9X_PLATFORM = "${@'armv7-neon' if d.getVar('TARGET_ARCH') == 'arm' else 'unix'}"

do_compile() {
    oe_runmake -C ${S}/libretro \
        platform=${SNES9X_PLATFORM} \
        LTO=""
}

do_install() {
    install -d ${D}${libdir}/libretro
    install -m 0755 ${S}/libretro/snes9x_libretro.so ${D}${libdir}/libretro/
}

FILES:${PN} = "${libdir}/libretro/snes9x_libretro.so"
