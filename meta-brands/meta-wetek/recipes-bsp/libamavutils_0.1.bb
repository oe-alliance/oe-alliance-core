SSUMMARY = "Amlogic audio video utils library"
PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PR = "r0"

inherit lib_package

SRC_URI = "http://sources.openelec.tv/devel/libamcodec-75f23da.tar.xz"

S = "${WORKDIR}/libamcodec-75f23da/amavutils"

SRC_URI[md5sum] = "55148d35b559e37efa1be016cbf90fe1"
SRC_URI[sha256sum] = "22080b09237a66de69e168e7b088c123ae88518c3bcaf60e09f7923bab1d2f53"

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
