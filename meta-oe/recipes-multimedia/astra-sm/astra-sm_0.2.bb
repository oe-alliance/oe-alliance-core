SUMMARY = "Astra (Advanced Streamer) SlonikMod"
DESCRIPTION = "Astra (Advanced Streamer) is a professional software to organize \
	Digital TV Service for TV operators and broadcasters, internet service providers, hotels, etc."
SECTION = "multimedia"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"
SRCREV = "0ec5137d33ca23b0d53e85daec38cb809be378e0"
DEPENDS = "libaio"

SRC_URI = "git://gitlab.com/berdyansk/astra-sm.git;protocol=http \
	file://version.patch \
	file://undef_dvb_net.patch \
	"

S = "${WORKDIR}/git"

inherit autotools-brokensep pkgconfig gettext

do_install_append() {
	install -m 0755 ${S}/tests/t2mi_decap ${D}${bindir}/t2mi_decap
}

FILES_${PN}-dev += "${datadir}"
