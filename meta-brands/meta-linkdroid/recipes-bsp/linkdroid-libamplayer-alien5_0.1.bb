SUMMARY = "Amlogic player library"
PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRCDATE = "20180513"
PR = "${SRCDATE}"

DEPENDS = "linkdroid-libamadec-${MACHINE} linkdroid-libamcodec-${MACHINE}"
RDEPENDS_${PN} = "linkdroid-libamadec-${MACHINE} linkdroid-libamcodec-${MACHINE}"

inherit lib_package pkgconfig

SRC_URI[md5sum] = "2400f6d95423b12c2f9996ba4dd2cd1c"
SRC_URI[sha256sum] = "5768fb5bc4e0ef5a7324f5b2fe0ab378224d29c2013848ff2e4447ce777569f5"

SRC_URI = "http://source.mynonpublic.com/linkdroid/${BPN}-${SRCDATE}.zip"

S = "${WORKDIR}"

EXTRA_OEMAKE = "\
    'CC=${CC}' \
    'LD=${LD}' \
    'CFLAGS=-fPIC -DENABLE_FREE_SCALE -I${S}/include -I${S}/../../amffmpeg -I${S}/player/ -I${S}/../../../media/amcodec/include -I${S}/../../../media/amcodec/codec \
    -I${S}/player/include -I${S} -I${STAGING_INCDIR}/amlogic/amcodec -I${S}/../../../LibAudio/amadec/include -I${S}/../../../media/amavutils/include' \
    'LDFLAGS=-lamadec -lm -lc  -shared -Wl,--shared ' \
"

do_install() {
    install -d ${D}${libdir}/pkgconfig
    install -m 0644 ${WORKDIR}/libamplayer.pc ${D}${libdir}/pkgconfig/
    install -d ${D}${includedir}/amlogic/player
    install -d ${D}${libdir}
    install -m 0755  ${WORKDIR}/libamplayer.so  ${D}${libdir}/
    cp -pR  ${S}/include/* ${D}${includedir}/amlogic/player
}

FILES_${PN} = "${libdir}/* "
FILES_${PN}-dev = "${includedir}/*"

do_configure() {
}

do_compile() {
}

do_package_qa() {
}

INSANE_SKIP_${PN} = "already-stripped dev-so ldflags dev-deps"