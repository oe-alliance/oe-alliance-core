SUMMARY = "Astra (Advanced Streamer) SlonikMod"
DESCRIPTION = "Astra (Advanced Streamer) is a professional software to organize \
	Digital TV Service for TV operators and broadcasters, internet service providers, hotels, etc."
SECTION = "multimedia"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

DEPENDS = "libaio"

SRCREV = "${AUTOREV}"

SRC_URI = "git://gitlab.com/berdyansk/astra-sm.git;protocol=http \
	file://version.patch \
	file://undef_dvb_net.patch \
	file://astra-sm \
	file://astra.conf \
	file://tools.patch \
	"

S = "${WORKDIR}/git"

inherit autotools-brokensep pkgconfig gettext

do_install_append() {
	install -m 0755 ${S}/tests/t2mi_decap ${D}${bindir}/t2mi_decap
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/astra-sm ${D}${sysconfdir}/init.d/
	install -m 0644 ${WORKDIR}/astra.conf ${D}${sysconfdir}/astra/
}

FILES_${PN} += "${sysconfdir}/init.d/"
FILES_${PN}-dev += "${datadir}"

CONFFILES_${PN} = "${sysconfdir}/astra/astra.conf"

INITSCRIPT_NAME = "astra-sm"
INITSCRIPT_PARAMS = "defaults"

CFLAGS_FOR_BUILD += "-std=c99"

inherit update-rc.d
