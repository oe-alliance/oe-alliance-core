SECTION = "base"
SUMMARY = "modutils configuration files"
SRC_URI = "file://modload.sh"
PV = "2"

require conf/license/license-gplv2.inc

INITSCRIPT_NAME = "modload.sh"
INITSCRIPT_PARAMS = "start 5 S ."

S = "${WORKDIR}"

inherit update-rc.d

do_compile () {
}

do_install () {
    install -d ${D}${sysconfdir}/init.d/
    install -m 0755 ${WORKDIR}/modload.sh ${D}${sysconfdir}/init.d/
}
