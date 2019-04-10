SUMMARY = "partitions by name"
DESCRIPTION = "for internal emmc flash which give partitons names in /dev/block/by-name/"
SECTION = "base"
require conf/license/license-gplv2.inc

SRC_URI = "file://partitions-by-name.sh"

INITSCRIPT_NAME = "partitions-by-name"
INITSCRIPT_PARAMS = "start 04 S ."

inherit update-rc.d

S = "${WORKDIR}"

do_install() {
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${S}/partitions-by-name.sh ${D}${sysconfdir}/init.d/partitions-by-name
}

do_package_qa() {
}

FILES_${PN} += " ${sysconfdir}/init.d"
