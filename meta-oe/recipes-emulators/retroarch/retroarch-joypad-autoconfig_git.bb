SUMMARY = "RetroArch udev joypad autoconfiguration for OpenATV"
HOMEPAGE = "https://github.com/libretro/retroarch-joypad-autoconfig"

LICENSE = "MIT AND Zlib"
LIC_FILES_CHKSUM = "file://COPYING;md5=a8f4fa937aea32ea575ff94a06499cce"

SRC_URI = "git://github.com/libretro/retroarch-joypad-autoconfig.git;protocol=https;branch=master"
SRCREV = "e889df929e9e4e3c21862c8ae062e60cabf451f5"
PV = "0.1+git20261008.${SRCPV}"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming', '.*', '^$', d)}"

do_compile[noexec] = "1"

do_install() {
    install -d ${D}${datadir}/libretro/autoconfig/udev
    install -m 0644 ${S}/udev/*.cfg ${D}${datadir}/libretro/autoconfig/udev/

    install -d ${D}${datadir}/libretro/autoconfig/linuxraw
    install -m 0644 ${S}/linuxraw/*.cfg ${D}${datadir}/libretro/autoconfig/linuxraw/
}

FILES:${PN} = "${datadir}/libretro/autoconfig"
