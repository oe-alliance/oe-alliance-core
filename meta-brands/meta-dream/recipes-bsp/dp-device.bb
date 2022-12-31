SUMMARY = "dp-device"
DESCRIPTION = "add ofgwrite dev node for online flash"
SECTION = "base"
require conf/license/license-gplv2.inc

SRC_URI = "file://dp-device.sh"

INITSCRIPT_NAME = "dp-device"
INITSCRIPT_PARAMS = "start 04 S ."

inherit update-rc.d

S = "${WORKDIR}"

do_install() {
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${S}/dp-device.sh ${D}${sysconfdir}/init.d/dp-device
}

do_package_qa() {
}

FILES:${PN} += " ${sysconfdir}/init.d"
