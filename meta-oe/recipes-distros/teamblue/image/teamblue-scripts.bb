SUMMARY = "Scripts package for teamBlue"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "teamBlue team"
require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = " \
    file://Standby.sh \
    "

FILES_${PN} = "/"
CONFFILES_${PN} = "/usr/script/Standby.sh"

S = "${WORKDIR}"

do_install() {
    install -d ${D}/usr/script
    for x in /Standby.sh; do
        install -m 0755 ${WORKDIR}/$x ${D}/usr/script/$x
    done
}

