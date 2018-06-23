SUMMARY = "halt for clap Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS_${PN} = "harfbuzz"

SRCDATE = "20180607"

PV = "${SRCDATE}"
PR = "r0"

SRC_URI  = "http://source.mynonpublic.com/clap/cc1-hihalt-${SRCDATE}.zip"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/hihalt ${D}/${bindir}
}

do_package_qa() {
}

FILES_${PN}  = "${bindir}/hihalt"

SRC_URI[md5sum] = "81ab3aa9dba22667a3a71b050320649a"
SRC_URI[sha256sum] = "413700d6f88d60d3d7a528fd7674bcee51ab45a1a1c3f03767c75b8042040e40"


