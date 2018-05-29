SUMMARY = "Amlogic audio decoders library"
PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PR = "r2"

DEPENDS = "wetek-libamavutils-${MACHINE} alsa-lib rtmpdump"
RDEPENDS_${PN} = "ffmpeg"

### for DTS encoder: don't check for stripped & text relocations
INSANE_SKIP_${PN} = "already-stripped textrel"

inherit lib_package pkgconfig

FWL_wetekplay = "firmware-m6"

EXTRA_OEMAKE = "\
    'CC=${CC}' \
    'CFLAGS=-O2 -fPIC -pthread -DALSA_OUT -DENABLE_WAIT_FORMAT -I${S}/include -I${S}' \
    'LDFLAGS=-shared -lamavutils -lpthread -lm -lasound -lrt -ldl' \
"

### NOTE: we are installing closed src DTS encoder as well for transcoding
do_install() {
    install -d ${D}${libdir}/pkgconfig
    install -d ${D}${includedir}/amlogic/amadec
    install -d ${D}${nonarch_base_libdir}/firmware
    install -d ${D}${libdir}
    install -m 0755 ${S}/include/* ${D}${includedir}/amlogic/amadec
    install -m 0755 ${S}/libamadec.so ${D}/${libdir}
    install -m 0755 ${S}/acodec_lib/*.so  ${D}/${libdir}
    if [ "${MACHINE}" = "wetekplay" ]; then
        install -m 0644 ${S}/${FWL}/*.bin  ${D}${nonarch_base_libdir}/firmware/
        install -m 0644 ${WORKDIR}/audiodsp_codec_ddp_dcv.bin ${D}${nonarch_base_libdir}/firmware/
    fi
    install -m 0644 ${WORKDIR}/libamadec.pc ${D}${libdir}/pkgconfig/
}

FILES_${PN} = "${libdir}/* ${nonarch_base_libdir}/firmware"
FILES_${PN}-dev = "${includedir}/*"

do_package_qa() {
}
