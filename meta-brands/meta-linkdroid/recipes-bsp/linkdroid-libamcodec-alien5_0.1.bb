SUMMARY = "Amlogic codecs library"
PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRCDATE = "20180531"
PR = "${SRCDATE}"

DEPENDS = "linkdroid-libamadec-${MACHINE}"
RDEPENDS_${PN} = "linkdroid-libamadec-${MACHINE}"

inherit lib_package pkgconfig

SRC_URI[md5sum] = "d2af892823d3e0ee25207a0c6aac7878"
SRC_URI[sha256sum] = "1f9b9fb5026676bc9d241b9790da96d94721d8e375b07ab97637410d64c45a4b"

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
