SUMMARY = "Merge machine and distro options to create a enigma2 devel feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY:${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "1.0"
PR = "r4"

inherit packagegroup

RRECOMMENDS:${PN} = "\
    enigma2 \
    enigma-info \
    opkg-arch-config \
    enigma2-plugin-skins-metrix-atv \
    enigma2-skins \
    enigma2-plugin-systemplugins-serviceapp \
    valgrind \
    \
    sysvinit \
    socketdaemon \
    "
