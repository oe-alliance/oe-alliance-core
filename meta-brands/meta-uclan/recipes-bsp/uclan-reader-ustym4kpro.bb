SUMMARY = "libreader for uclan Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS_${PN} = "libsdl"

COMPATIBLE_MACHINE = "^(ustym4kpro)$"

SRCDATE = "20200415"

PV = "${SRCDATE}"
PR = "r0"

SRC_URI = "http://source.mynonpublic.com/uclan/${MACHINE}-libreader-${SRCDATE}.zip"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/libreader ${D}/${bindir}
}

do_package_qa() {
}

FILES_${PN}  = "${bindir}/libreader"

SRC_URI[md5sum] = "dd6a53f18e59bd53f23166e5d3c4231f"
SRC_URI[sha256sum] = "bc34683446e1440644b5a5d7c5ff31a3d73a0a25d089b5fc189ef24fcb827c91"
