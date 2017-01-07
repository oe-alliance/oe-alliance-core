SUMMARY = "Amlogic codecs library"
PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PR = "r2"

DEPENDS = "libamadec"
RDEPENDS_${PN} = "libamadec"

inherit lib_package

SRC_URI_wetekplay = "file://libamcodec-75f23da.tar.gz;md5=2ff1cbc415271733e1241e8cde0b105e \
           file://add-vformat-hevc.patch \
           file://libamcodec.pc \
           file://alsactl.conf \
"
SRC_URI_wetekplay2 = "file://libamcodec-210755d.tar.gz;md5=d2e7dc15302fa64eef54aa67da5f9f34 \
           file://libamcodec.pc \
           file://alsactl.conf \
"
SRC_URI_odroidc2 = "file://libamcodec-210755d.tar.gz;md5=d2e7dc15302fa64eef54aa67da5f9f34 \
           file://libamcodec.pc \
           file://alsactl.conf \
"

S_wetekplay = "${WORKDIR}/libamcodec-75f23da/amcodec"
S_wetekplay2 = "${WORKDIR}/libamcodec-210755d/amcodec"
S_odroidc2 = "${WORKDIR}/libamcodec-210755d/amcodec"


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
