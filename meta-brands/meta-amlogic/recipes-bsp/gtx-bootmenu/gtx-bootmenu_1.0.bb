SUMMARY = "Infrared multiboot menu for the GTMedia GTX Combo"
LICENSE = "Apache-2.0 AND GPL-3.0-only"
LIC_FILES_CHKSUM = "file://${UNPACKDIR}/dream/LICENSE;md5=c79ff39f19dfec6d293b95dea7b07891 \
    file://${UNPACKDIR}/dream/fonts/LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10"
COMPATIBLE_MACHINE = "^gtxcombo$"
PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit gitpkgv deploy python3native
DEPENDS = "python3-pillow-native u-boot-tools-native"

SRC_URI = "git://github.com/oe-alliance/gtx-bootmenu.git;protocol=https;branch=master;name=menu;destsuffix=menu \
    git://github.com/oe-alliance/dreambox-bootmenu.git;protocol=https;nobranch=1;name=dream;destsuffix=dream"
SRCREV_menu = "${AUTOREV}"
SRCREV_dream = "e951143d33d2b7a16c2c781e77bae8186a8f34df"
SRCREV_FORMAT = "menu+dream"
PV = "git"
PKGV = "git${GITPKGV}"
PR = "r1"
S = "${UNPACKDIR}/menu"
B = "${WORKDIR}/build"

do_compile() {
    CROSS_COMPILE="${TARGET_PREFIX}" sh ${S}/build.sh ${UNPACKDIR}/dream ${B}
}
do_deploy() {
    install -d ${DEPLOYDIR}/gtx-bootmenu
    install -m 0644 ${B}/gtx/bootmenu.bin ${B}/gtx/bootmenu.scr ${DEPLOYDIR}/gtx-bootmenu/
}
addtask deploy after do_compile before do_build
