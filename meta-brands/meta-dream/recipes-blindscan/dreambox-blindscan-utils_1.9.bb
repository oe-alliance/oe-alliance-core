SUMMARY = "Utilities needed to do transponder blindscan with dreambox dvb receivers"
LICENSE = "CLOSED"

PACKAGE_ARCH = "${MACHINE_ARCH}"

PROVIDES += "virtual/blindscan-dvbs virtual/blindscan-dvbc"
RPROVIDES_${PN} += "virtual/blindscan-dvbs virtual/blindscan-dvbc"

DEPENDS = "ncurses"

PR = "r3"

SRC_URI += "http://dreamboxupdate.com/download/opendreambox/2.0.0/blindscan-utils/blindscan-utils_${PV}_${DEFAULTTUNE}.tar.bz2;name=${DEFAULTTUNE}"

S = "${WORKDIR}/blindscan-utils_${PV}_${DEFAULTTUNE}"

PACKAGES = "${PN}"

SRC_URI[mips32el-nf.md5sum] = "143cb7253132af1ecd3aafa3679c6109"
SRC_URI[mips32el-nf.sha256sum] = "53d2760e3aa19eab6e19edabe8b9dd840c693ca30c43495904241e52fd40ea32"
SRC_URI[mips32el.md5sum] = "1dd11bc63e8a638f240689e9a00969e4"
SRC_URI[mips32el.sha256sum] = "a87d8b6440c1dab6c7e3e56bdb483f2089185ff55cb64df6321750092b4bd6e0"

do_install() {
    cp -r * ${D}
}

INHIBIT_PACKAGE_STRIP = "1"
