SSUMMARY = "Amlogic audio video utils library"
PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRCDATE = "20180513"

PV = "0"
PR = "${SRCDATE}"

inherit lib_package

SRC_URI[md5sum] = "11f1c21749716cb05d6a77fd326bfc15"
SRC_URI[sha256sum] = "95115c31891d4ed2234a4a6b70ef587ced28c3d943ad093c1a4d0d2b568d4b5e"

SRC_URI = "http://source.mynonpublic.com/linkdroid/${BPN}-${SRCDATE}.zip"


S = "${WORKDIR}"

do_install() {
    install -d ${D}${libdir}
    install -m 0755 ${WORKDIR}/libaac_helix.so ${D}${libdir}/
    install -m 0755 ${WORKDIR}/libadpcm.so ${D}${libdir}/
    install -m 0755 ${WORKDIR}/libamr.so ${D}${libdir}/
    install -m 0755 ${WORKDIR}/libape.so ${D}${libdir}/
    install -m 0755 ${WORKDIR}/libcook.so ${D}${libdir}/
    install -m 0755 ${WORKDIR}/libfaad_aml.so ${D}${libdir}/
    install -m 0755 ${WORKDIR}/libflac.so ${D}${libdir}/
    install -m 0755 ${WORKDIR}/libpcm_wfd.so ${D}${libdir}/
    install -m 0755 ${WORKDIR}/libmad_aml.so ${D}${libdir}/
    install -m 0755 ${WORKDIR}/libpcm.so ${D}${libdir}/
    install -m 0755 ${WORKDIR}/libraac.so ${D}${libdir}/
    install -m 0755 ${WORKDIR}/libac3.so ${D}${libdir}/
    install -m 0755 ${WORKDIR}/libdts.so ${D}${libdir}/
    install -m 0755 ${WORKDIR}/libdtshd.so ${D}${libdir}/
}

FILES_${PN} += "${libdir}/*"
FILES_${PN}-dev = ""

do_configure() {
}

do_compile() {
}

do_package_qa() {
}

INSANE_SKIP_${PN} = "already-stripped dev-so ldflags dev-deps"