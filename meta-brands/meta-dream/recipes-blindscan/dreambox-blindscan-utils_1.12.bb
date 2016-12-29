SUMMARY = "Utilities needed to do transponder blindscan with dreambox dvb receivers"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGE_ARCH = "${MACHINE_ARCH}"

PROVIDES += "virtual/blindscan-dvbs virtual/blindscan-dvbc"
RPROVIDES_${PN} += "virtual/blindscan-dvbs virtual/blindscan-dvbc"

DEPENDS = "ncurses"

PR = "r4"

SRC_URI_dm800 += "http://dreamboxupdate.com/download/opendreambox/2.0.0/blindscan-utils/blindscan-utils_${PV}_${DEFAULTTUNE}.tar.bz2;name=${DEFAULTTUNE}"
SRC_URI += "http://dreamboxupdate.com/download/opendreambox/2.2.0/blindscan-utils/${PV}/${DEFAULTTUNE}/c4176cf87b943e166b49e15624869243/blindscan-utils_${PV}_${DEFAULTTUNE}.tar.xz;name=${DEFAULTTUNE}"
SRC_URI_dm900 += "http://dreamboxupdate.com/download/opendreambox/2.5.0/blindscan-utils/${PV}/${DEFAULTTUNE}/893ae9303ba95ce54da3c2736f32a090/blindscan-utils_${PV}_${DEFAULTTUNE}.tar.xz;name=${DEFAULTTUNE}"

S = "${WORKDIR}/blindscan-utils_${PV}_${DEFAULTTUNE}"

PACKAGES = "${PN}"

SRC_URI[mips32el-nf.md5sum] = "143cb7253132af1ecd3aafa3679c6109"
SRC_URI[mips32el-nf.sha256sum] = "53d2760e3aa19eab6e19edabe8b9dd840c693ca30c43495904241e52fd40ea32"
SRC_URI[cortexa15hf-neon-vfpv4.md5sum] = "893ae9303ba95ce54da3c2736f32a090"
SRC_URI[cortexa15hf-neon-vfpv4.sha256sum] = "7271fb06f56d2398f8cdf4e328649ab27722d690f87c24e1734956dced21b947"
SRC_URI[mips32el.md5sum] = "c4176cf87b943e166b49e15624869243"
SRC_URI[mips32el.sha256sum] = "1f27288a90011444c4e241e0e9a2a79f1a7baed596d0003e2e3b666819afe8b9"

do_install() {
    cp -r * ${D}
}

INHIBIT_PACKAGE_STRIP = "1"
