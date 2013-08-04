DESCRIPTION = "Start, stop and select softcams."
MAINTAINER = "PLi team"

require conf/license/openpli-gplv2.inc

inherit allarch

PACKAGES = "${PN}"

PV = "1.0"
PR = "r1"

INITSCRIPT_NAME = "softcam"
INITSCRIPT_PARAMS = "defaults 50"
inherit update-rc.d

FILES_${PN} = "/etc"

do_install() {
	install -d ${D}/etc/init.d
	install -m 755 ${S}/softcam.None ${D}/etc/init.d/softcam.None
	ln -s softcam.None ${D}/etc/init.d/softcam
}

do_compile_append() {
	echo "# Placeholder for no cam" > ${S}/softcam.None
}
