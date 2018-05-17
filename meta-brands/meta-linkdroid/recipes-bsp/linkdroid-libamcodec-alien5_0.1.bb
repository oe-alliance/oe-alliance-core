SUMMARY = "Amlogic codecs library"
PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRCDATE = "20180516"
PR = "${SRCDATE}"

DEPENDS = "linkdroid-libamadec-${MACHINE}"
RDEPENDS_${PN} = "linkdroid-libamadec-${MACHINE}"

inherit lib_package pkgconfig

SRC_URI[md5sum] = "0d32b023421f2a8ca095128c4f414b71"
SRC_URI[sha256sum] = "608ff5ae9ed679048825a23ec6278cc6e53b59ce0ab0195e569991d1aa52c8d2"

SRC_URI = "http://source.mynonpublic.com/linkdroid/${BPN}-${SRCDATE}.zip"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${libdir}/pkgconfig
    install -d ${D}${sysconfdir}/
    install -m 0644 ${WORKDIR}/libamcodec.pc ${D}${libdir}/pkgconfig/
    install -d ${D}${includedir}/amlogic/amcodec
    install -d ${D}${includedir}/amcodec
    cp -pR ${S}/include/* ${D}${includedir}/amlogic/amcodec
    cp -pR ${S}/include/* ${D}${includedir}/amcodec
    install -d ${D}${libdir}
    install -m 0755  ${WORKDIR}/libamcodec.so.0.0  ${D}${libdir}/
    cd ${D}${libdir}
    ln -sf libamcodec.so.0.0 libamcodec.so
}

do_configure() {
}

do_compile() {
}

do_package_qa() {
}

INSANE_SKIP_${PN} = "already-stripped dev-so ldflags dev-deps"
