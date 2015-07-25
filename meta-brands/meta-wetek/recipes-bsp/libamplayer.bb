SUMMARY = "Amlogic player library"
PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PR = "r0"

DEPENDS = "libamadec libamcodec"
RDEPENDS_${PN} = "libamadec libamcodec"

inherit lib_package

SRC_URI = "http://sources.openelec.tv/devel/libamcodec-75f23da.tar.xz \
           file://00-amplayer-makefile-fixes.patch \
           file://libamplayer.pc \
"

S = "${WORKDIR}/libamcodec-75f23da/amplayer"

SRC_URI[md5sum] = "55148d35b559e37efa1be016cbf90fe1"
SRC_URI[sha256sum] = "22080b09237a66de69e168e7b088c123ae88518c3bcaf60e09f7923bab1d2f53"

EXTRA_OEMAKE = "\
    'CC=${CC}' \
    'CFLAGS=-fPIC -DENABLE_FREE_SCALE -I${S}/include -I${S}/../amffmpeg -I${S}/player/ \
    -I${S}/player/include -I${S} -I${STAGING_INCDIR}/amlogic/amcodec' \
    'LDFLAGS=-lamadec -lm -lc  -shared -Wl,--shared ' \
"

do_install() {
    install -d ${D}${libdir}/pkgconfig
    install -m 0644 ${WORKDIR}/libamplayer.pc ${D}${libdir}/pkgconfig/
    install -d ${D}${includedir}/amlogic/player
    install -d ${D}${libdir}
    install -m 0755  ${S}/libamplayer.so  ${D}${libdir}
    cp -pR  ${S}/player/include/* ${D}${includedir}/amlogic/player
}

FILES_${PN} = "${libdir}/* "
FILES_${PN}-dev = "${includedir}/*"
