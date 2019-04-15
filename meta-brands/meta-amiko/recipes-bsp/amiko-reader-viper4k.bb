SUMMARY = "libreader for AMIKO Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS_${PN} = "libsdl"

COMPATIBLE_MACHINE = "^(viper4k)$"

SRCDATE = "20190415"

PV = "${SRCDATE}"
PR = "r0"

SRC_URI = "http://source.mynonpublic.com/amiko/${MACHINE}-libreader-${SRCDATE}.tar.gz"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/libreader ${D}/${bindir}
}

do_package_qa() {
}

FILES_${PN}  = "${bindir}/libreader"

SRC_URI[md5sum] = "355f68bf4993e70e2252a45578a9289b"
SRC_URI[sha256sum] = "8d86f99ef5052686f056db96d3b2a96fb58338fdafc92ce77ac37273767cc30a"
