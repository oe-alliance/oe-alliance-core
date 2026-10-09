SUMMARY = "ScummVM libretro core"
DESCRIPTION = "ScummVM adventure game engine packaged as a libretro core."
HOMEPAGE = "https://github.com/libretro/scummvm"

LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=1ebbd3e34237af26da5dc08a4e440464"

SRCREV_scummvm = "fcbce3ae815269dacdc309092bc92ccc6d3e13bb"
SRCREV_libretro_deps = "e639e0c26fdbbacd7a9ab70a831c4def406a4e3e"
SRCREV_libretro_common = "2b96a82bd8479bb3547d271e1eefadc82dd2161e"
SRCREV_FORMAT = "scummvm_libretro_deps_libretro_common"

SRC_URI = " \
    git://github.com/libretro/scummvm.git;protocol=https;branch=master;name=scummvm \
    git://github.com/libretro/libretro-deps;protocol=https;nobranch=1;name=libretro_deps;destsuffix=${BP}/backends/platform/libretro/deps/libretro-deps \
    git://github.com/libretro/libretro-common;protocol=https;nobranch=1;name=libretro_common;destsuffix=${BP}/backends/platform/libretro/deps/libretro-common \
"
PV = "0.1+git20261008.${SRCPV}"
PR = "r1"

require libretro-core.inc

DEPENDS += "zip-native unzip-native"

LIBRETRO_CORE_FILE = "scummvm_libretro.so"
LIBRETRO_CORE_PATH = "${S}/backends/platform/libretro/${LIBRETRO_CORE_FILE}"
SCUMMVM_LITE = "${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming-highperformance', '0', '1', d)}"

do_configure() {
    :
}

do_compile() {
    oe_runmake -C ${S}/backends/platform/libretro \
        platform=unix \
        LITE=${SCUMMVM_LITE} \
        FORCE_OPENGLNONE=1

    install -d ${B}/libretro-system-data
    bash ${S}/backends/platform/libretro/scripts/bundle_datafiles.sh \
        ${B}/libretro-system-data ${S} bundle
}

do_install:append() {
    install -d ${D}${datadir}/libretro
    unzip -q ${B}/libretro-system-data/scummvm.zip \
        -d ${D}${datadir}/libretro
}

FILES:${PN} += "${datadir}/libretro/scummvm"
