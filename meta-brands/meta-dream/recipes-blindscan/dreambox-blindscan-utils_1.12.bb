SUMMARY = "Utilities needed to do transponder blindscan with dreambox dvb receivers"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGE_ARCH = "${MACHINE_ARCH}"

PROVIDES += "virtual/blindscan-dvbs virtual/blindscan-dvbc"
RPROVIDES:${PN} += "virtual-blindscan-dvbs virtual-blindscan-dvbc"

DEPENDS = "ncurses"

PR = "r4"

SRC_URI += "https://source.mynonpublic.com/dreambox/blindscan-utils_${PV}_${DEFAULTTUNE}.tar.xz;name=${DEFAULTTUNE}-dora"
SRC_URI:dm900 += "https://source.mynonpublic.com/dreambox/blindscan-utils_${PV}_${DEFAULTTUNE}.tar.xz;name=${DEFAULTTUNE}-krogoth"
SRC_URI:dm920 += "https://source.mynonpublic.com/dreambox/blindscan-utils_${PV}_${DEFAULTTUNE}.tar.xz;name=${DEFAULTTUNE}-krogoth"
SRC_URI:dm520 += "https://source.mynonpublic.com/dreambox/dm520/blindscan-utils_${PV}_${DEFAULTTUNE}.tar.xz;name=${DEFAULTTUNE}-krogoth"
SRC_URI:dm820 += "https://source.mynonpublic.com/dreambox/dm820/blindscan-utils_${PV}_${DEFAULTTUNE}.tar.xz;name=${DEFAULTTUNE}-krogoth"
SRC_URI:dm7080 += "https://source.mynonpublic.com/dreambox/dm7080/blindscan-utils_${PV}_${DEFAULTTUNE}.tar.xz;name=${DEFAULTTUNE}-krogoth"
SRC_URI:dreamone += "https://source.mynonpublic.com/dreambox/blindscan-utils_${PV}_${DEFAULTTUNE}.tar.xz;name=${DEFAULTTUNE}-pyro"
SRC_URI:dreamtwo += "https://source.mynonpublic.com/dreambox/blindscan-utils_${PV}_${DEFAULTTUNE}.tar.xz;name=${DEFAULTTUNE}-pyro"

S = "${WORKDIR}/blindscan-utils_${PV}_${DEFAULTTUNE}"

PACKAGES = "${PN}"

SRC_URI[aarch64-pyro.md5sum] = "aa6dad1ece041c236338d3a41fd5da1e"
SRC_URI[aarch64-pyro.sha256sum] = "aeaf9088b3cb2bf91dcab7e7ae8d417ab7eaca304f2f9dc35e5e14838ce5f0cc"
SRC_URI[mips32el-dora.md5sum] = "2acb58192434f308bfed6879c51d5d6e"
SRC_URI[mips32el-dora.sha256sum] = "afe56c1d222c4d6d33509208116628c30f88916bda7a19e5c749dcbc914d6020"
SRC_URI[cortexa15hf-neon-vfpv4-krogoth.md5sum] = "9e93783a6ac4611bb683d0b36fc44a87"
SRC_URI[cortexa15hf-neon-vfpv4-krogoth.sha256sum] = "ff4de20d84cf6aef48270684cb78764f7ee9d13d92fab42217f378be06047d1f"
SRC_URI[mips32el-krogoth.md5sum] = "185cd9490723728f39953348e322f5c7"
SRC_URI[mips32el-krogoth.sha256sum] = "0458975ad8325355a5e9514fa4e3505f896298bdad7610718e090b640e3588c5"

do_install() {
    cp -r * ${D}
}

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

INSANE_SKIP = "ldflags 32bit-time"

