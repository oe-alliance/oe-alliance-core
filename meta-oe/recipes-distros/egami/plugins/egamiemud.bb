DESCRIPTION="EGAMI Emud - start stop softcams (base on BH  Socker)"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "EGAMI Team"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

PR = "r7"

SRC_URI="file://egamiemud.tar.gz"

S = "${WORKDIR}/egamiemud"
S2 = "${WORKDIR}/build"

inherit autotools update-rc.d

INITSCRIPT_NAME = "egamiemud"
INITSCRIPT_PARAMS = "defaults"

FILES_${PN} = "/"

do_compile() {
    oe_runmake
    ${STRIP} egamiemud
}

do_install() {
	mkdir -p ${D}/bin/
	install -m 0755 ${S2}/egamiemud ${D}/bin/emud
	mkdir -p ${D}/etc/init.d
	install -m 0755 ${S}/egamiemud.sh ${D}/etc/init.d/egamiemud
}
