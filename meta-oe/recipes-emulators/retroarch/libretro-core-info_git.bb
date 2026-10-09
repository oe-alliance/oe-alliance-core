SUMMARY = "Libretro core metadata for the OpenATV RetroGaming feed"
HOMEPAGE = "https://github.com/libretro/libretro-core-info"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=459277d80461c2908b4cf14949f8dcd5"

SRC_URI = "git://github.com/libretro/libretro-core-info.git;protocol=https;branch=master"
SRCREV = "5a74858ab2f7a50cebb5a6330895bc38899531c0"
PV = "0.1+git20261008.${SRCPV}"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming', '.*', '^$', d)}"

do_compile[noexec] = "1"

do_install() {
    install -d ${D}${datadir}/libretro/info
    install -m 0644 ${S}/*.info ${D}${datadir}/libretro/info/
}

FILES:${PN} = "${datadir}/libretro/info"
