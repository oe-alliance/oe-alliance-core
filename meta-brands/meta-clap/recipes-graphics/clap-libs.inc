DESCRIPTION = "shared lib for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS_${PN} = "curl"

PV = "${SRCDATE}"
PR = "r1"

inherit lib_package

SRC_URI = "http://source.mynonpublic.com/clap/${MACHINE}-hilib-${SRCDATE}.tar.gz"

S = "${WORKDIR}/hilib"

do_configure() {
}

do_compile() {
}

do_install() {
	install -d ${D}${libdir}
	install -m 0755 ${S}/*.so ${D}${libdir}/


	cd ${D}${libdir}

	ln -sf libHA.AUDIO.AAC.decode.so libHA.AUDIO.AAC.decode.so.0.0 
	ln -sf libHA.AUDIO.AAC.encode.so libHA.AUDIO.AAC.encode.so.0.0 
	ln -sf libHA.AUDIO.AC33.decode.so libHA.AUDIO.AC33.decode.so.0.0
	ln -sf libHA.AUDIO.AMRNB.codec.so libHA.AUDIO.AMRNB.codec.so.0.0
	ln -sf libHA.AUDIO.AMRWB.codec.so libHA.AUDIO.AMRWB.codec.so.0.0
	ln -sf libHA.AUDIO.BLURAYLPCM.decode.so libHA.AUDIO.BLURAYLPCM.decode.so.0.0
	ln -sf libHA.AUDIO.COOK.decode.so libHA.AUDIO.COOK.decode.so.0.0
	ln -sf libHA.AUDIO.DTSS.decode.so libHA.AUDIO.DTSS.decode.so.0.0
	ln -sf libHA.AUDIO.G711.codec.so libHA.AUDIO.G711.codec.so.0.0
	ln -sf libHA.AUDIO.G722.codec.so libHA.AUDIO.G722.codec.so.0.0
	ln -sf libHA.AUDIO.MP2.decode.so libHA.AUDIO.MP2.decode.so.0.0
	ln -sf libHA.AUDIO.MP3.decode.so libHA.AUDIO.MP3.decode.so.0.0
	ln -sf libHA.AUDIO.OPUS.codec.so libHA.AUDIO.OPUS.codec.so.0.0
	ln -sf libHA.AUDIO.PCM.decode.so libHA.AUDIO.PCM.decode.so.0.0
	ln -sf libHA.AUDIO.TRUEHD.decode.so libHA.AUDIO.TRUEHD.decode.so.0.0
	ln -sf libHA.AUDIO.VOICE.codec.so libHA.AUDIO.VOICE.codec.so.0.0
	ln -sf libHA.AUDIO.VORBIS.codec.so libHA.AUDIO.VORBIS.codec.so.0.0

	ln -sf libhi_common.so libhi_common.so.0.0
	ln -sf libhi_msp.so libhi_msp.so.0.0
}

do_package_qa() {
}

FILES_${PN} += "${libdir}/*"

INSANE_SKIP_${PN} = "already-stripped dev-so"
