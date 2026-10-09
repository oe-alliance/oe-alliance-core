SUMMARY = "Space-optimised RetroArch RGUI assets for OpenATV"
DESCRIPTION = "The RGUI and font subset of the official RetroArch assets, avoiding the much larger desktop themes and wallpapers."
HOMEPAGE = "https://github.com/libretro/retroarch-assets"

LICENSE = "CC-BY-4.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=7bd61880991ed797753fcc00acae2c51"

SRC_URI = "git://github.com/libretro/retroarch-assets.git;protocol=https;branch=master"
SRCREV = "d9f969054dc7fbb6fa89519036d2b971e0855b51"
PV = "0.1+git20261008.${SRCPV}"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming', '.*', '^$', d)}"

do_compile[noexec] = "1"

do_install() {
    install -d ${D}${datadir}/libretro/assets/rgui
    cp -R --no-preserve=ownership ${S}/rgui/. ${D}${datadir}/libretro/assets/rgui/

    install -d ${D}${datadir}/libretro/assets/fonts
    cp -R --no-preserve=ownership ${S}/fonts/. ${D}${datadir}/libretro/assets/fonts/
}

FILES:${PN} = "${datadir}/libretro/assets"
