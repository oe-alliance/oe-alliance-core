SUMMARY = "Amlogic audio decoders library"
PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRCDATE = "20180905"
PR = "${SRCDATE}"

DEPENDS = "linkdroid-libamavutils-${MACHINE} alsa-lib rtmpdump "
RDEPENDS_${PN} = "ffmpeg linkdroid-libamavutils-${MACHINE}"


inherit lib_package

SRC_URI[md5sum] = "cdfbaaf67bfa803526219f3b492012bf"
SRC_URI[sha256sum] = "ab41d06abaeff8d2489a57e7e83022955aeddd5ade7bb448c0fcd79e3a38fabb"

SRC_URI = "http://source.mynonpublic.com/linkdroid/${BPN}-${SRCDATE}.zip"

S = "${WORKDIR}/"

do_install() {
    install -d ${D}${libdir}/pkgconfig
    install -d ${D}${includedir}/amlogic/amadec
    install -d ${D}${includedir}/amlogic/amadec/amports
    install -d ${D}${includedir}/amlogic/amadec/system
    install -d ${D}${libdir}
    install -m 0755 ${S}/include/*.h ${D}${includedir}/amlogic/amadec/
    install -m 0755 ${WORKDIR}/libamadec.so ${D}/${libdir}/
    install -m 0644 ${WORKDIR}/libamadec.pc ${D}${libdir}/pkgconfig/
}

FILES_${PN} = "${libdir}/* "
FILES_${PN}-dev = "${includedir}/*"

do_configure() {
}

do_compile() {
}

do_package_qa() {
}

INSANE_SKIP_${PN} = "already-stripped dev-so ldflags dev-deps textrel"

SOLIBS = ".so"
FILES_SOLIBSDEV = ""
