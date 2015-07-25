SUMMARY = "Amlogic codecs library"
PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PR = "r1"

DEPENDS = "libamadec"
RDEPENDS_${PN} = "libamadec"

inherit lib_package

SRC_URI = "http://sources.openelec.tv/devel/libamcodec-75f23da.tar.xz \
           file://00-makefile-fix.patch \
           file://libamcodec.pc \
           file://alsactl.conf \
"

S = "${WORKDIR}/libamcodec-75f23da/amcodec"

SRC_URI[md5sum] = "55148d35b559e37efa1be016cbf90fe1"
SRC_URI[sha256sum] = "22080b09237a66de69e168e7b088c123ae88518c3bcaf60e09f7923bab1d2f53"

EXTRA_OEMAKE = " \
    'CC=${CC}' \
    'CFLAGS=-O2 -fPIC -I${S}/include -I${S} -I${S}/codec -I${STAGING_INCDIR}/amlogic/amadec ' \
    'LDFLAGS=-lamadec -lm -lc  -shared -Wl,--shared -Wl,-soname,libamcodec.so' \
"

do_install() {
    install -d ${D}${libdir}/pkgconfig
    install -d ${D}${sysconfdir}/
    install -m 0644 ${WORKDIR}/alsactl.conf ${D}${sysconfdir}/
    install -m 0644 ${WORKDIR}/libamcodec.pc ${D}${libdir}/pkgconfig/
    install -d ${D}${includedir}/amlogic/amcodec
    cp -pR ${S}/include/* ${D}${includedir}/amlogic/amcodec
    install -d ${D}${libdir}
    install -m 0755  ${S}/libamcodec.so.0.0  ${D}${libdir}
    cd ${D}${libdir}
    ln -sf libamcodec.so.0.0 libamcodec.so
}
