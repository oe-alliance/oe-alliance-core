DESCRIPTION="Blackholesocker: External bin to execute commands sent by Enigma2 via socket"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "Black Hole team"

require conf/license/license-gplv2.inc

PR = "r1"
PV = "0.1"

BRANCH = "master"
SRCREV = ""

SRC_URI="file://blackholesocker.tar.gz"


S = "${WORKDIR}/blackholesocker"
S2 = "${WORKDIR}/build"

inherit autotools update-rc.d

INITSCRIPT_NAME = "${PN}"
INITSCRIPT_PARAMS = "defaults 60"

FILES_${PN} = "/"

do_install() {

	install -d ${D}/usr/bin
	install -m 0755 ${S2}/blackholesocker ${D}/usr/bin/blackholesocker
	install -d ${D}/etc/init.d
	install -m 0755 ${S}/${PN}.sh ${D}/etc/init.d/${PN}
}
