SSUMMARY = "Amlogic audio video utils library"
PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PR = "r0"

inherit lib_package

SRC_URI_wetekplay = "file://libamcodec-75f23da.tar.gz;md5=2ff1cbc415271733e1241e8cde0b105e"
SRC_URI_wetekplay2 = "file://libamcodec-210755d.tar.gz;md5=d2e7dc15302fa64eef54aa67da5f9f34"
SRC_URI_odroidc2 = "file://libamcodec-210755d.tar.gz;md5=d2e7dc15302fa64eef54aa67da5f9f34"

S_wetekplay = "${WORKDIR}/libamcodec-75f23da/amavutils"
S_wetekplay2 = "${WORKDIR}/libamcodec-210755d/amavutils"
S_odroidc2 = "${WORKDIR}/libamcodec-210755d/amavutils"

EXTRA_OEMAKE = "\
    'CC=${CC}' \
    'CFLAGS=-O2 -fPIC -DALSA_OUT -DENABLE_WAIT_FORMAT -I${S}/../amcodec/include -I${S}/include -I${S}' \
    'LDFLAGS=-shared -lm -lrt' \
"

do_install() {
    install -d ${D}${includedir}
    install -d ${D}${libdir}
    cp -PR ${S}/include ${D}/usr
    install -m 0755 ${S}/libamavutils.so ${D}/${libdir}
}

FILES_${PN} = "${includedir}/* ${libdir}/* "
FILES_${PN}-dev = "/usr/include/*"
FILES_${PN}-src = ""
