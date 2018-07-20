DESCRIPTION = "shared lib for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS_${PN} = "curl"

PV = "${SRCDATE}"
PR = "r1"

inherit lib_package pkgconfig

SRC_URI = "http://source.mynonpublic.com/dinobot/${MACHINE}-libs-${SRCDATE}.zip"

S = "${WORKDIR}/hilib"

SOLIBS = ".so"
FILES_SOLIBSDEV = ""

do_configure() {
}

do_compile() {
}

do_install() {
	install -d ${D}${libdir}
	install -m 0755 ${S}/*.so ${D}${libdir}/
	install -m 0755 ${S}/libchipid.so.0.0.0  ${D}${libdir}/
	install -m 0755 ${S}/avdec ${D}${libdir}/
	install -m 0755 ${S}/libhi_common.so ${D}${libdir}/
	install -m 0755 ${S}/libhi_msp.so ${D}${libdir}/
	install -m 0755 ${S}/libjpeg9b.so ${D}${libdir}/

	install -d ${D}${libdir}/pkgconfig
	cp -r ${WORKDIR}/pkgconfig/*.pc ${D}${libdir}/pkgconfig/

	cd ${D}${libdir}

	ln -sf libHA.AUDIO.AAC.decode.so libHA.AUDIO.AAC.decode.so.0.0 
	ln -sf libHA.AUDIO.AAC.encode.so libHA.AUDIO.AAC.encode.so.0.0 
	ln -sf libHA.AUDIO.AC3PASSTHROUGH.decode.so libHA.AUDIO.AC3PASSTHROUGH.decode.so.0.0
	ln -sf libHA.AUDIO.AMRNB.codec.so libHA.AUDIO.AMRNB.codec.so.0.0
	ln -sf libHA.AUDIO.AMRWB.codec.so libHA.AUDIO.AMRWB.codec.so.0.0
	ln -sf libHA.AUDIO.BLURAYLPCM.decode.so libHA.AUDIO.BLURAYLPCM.decode.so.0.0
	ln -sf libHA.AUDIO.COOK.decode.so libHA.AUDIO.COOK.decode.so.0.0
	ln -sf libHA.AUDIO.DTSPASSTHROUGH.decode.so libHA.AUDIO.DTSPASSTHROUGH.decode.so.0.0
	ln -sf libHA.AUDIO.G711.codec.so libHA.AUDIO.G711.codec.so.0.0
	ln -sf libHA.AUDIO.G722.codec.so libHA.AUDIO.G722.codec.so.0.0
	ln -sf libHA.AUDIO.MP2.decode.so libHA.AUDIO.MP2.decode.so.0.0
	ln -sf libHA.AUDIO.MP3.decode.so libHA.AUDIO.MP3.decode.so.0.0
	ln -sf libHA.AUDIO.PCM.decode.so libHA.AUDIO.PCM.decode.so.0.0
	ln -sf libHA.AUDIO.TRUEHDPASSTHROUGH.decode.so libHA.AUDIO.TRUEHDPASSTHROUGH.decode.so.0.0
	ln -sf libHA.AUDIO.VOICE.codec.so libHA.AUDIO.VOICE.codec.so.0.0

	ln -sf libhi_common.so libhi_common.so.0.0
	ln -sf libhi_msp.so libhi_msp.so.0.0
	ln -sf libjpeg9b.so libjpeg9b.so.0.0
	ln -sf libchipid.so.0.0.0 libchipid.so 
}

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

do_package_qa() {
}

FILES_${PN} += "${libdir}/*"

INSANE_SKIP_${PN} = "already-stripped dev-so ldflags dev-deps"
