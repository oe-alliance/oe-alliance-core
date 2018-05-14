SSUMMARY = "Amlogic audio video utils library"
PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRCDATE = "20180513"
PR = "${SRCDATE}"

inherit lib_package

SRC_URI[md5sum] = "66a3d254b25d6bf0e7b05a4af030bc38"
SRC_URI[sha256sum] = "530cbe8d545fdb76fba44cf9830e063a7217868078e02f7207bf5a3b9f299b2b"

SRC_URI = "http://source.mynonpublic.com/linkdroid/${BPN}-${SRCDATE}.zip"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${includedir}
    install -d ${D}${libdir}
    cp -PR ${S}/include ${D}/usr
    install -m 0755 ${WORKDIR}/libamavutils.so ${D}/${libdir}/
}

FILES_${PN} = "${includedir}/* ${libdir}/* "
FILES_${PN}-dev = "/usr/include/*"

do_configure() {
}

do_compile() {
}

do_package_qa() {
}

INSANE_SKIP_${PN} = "already-stripped dev-so ldflags dev-deps"
