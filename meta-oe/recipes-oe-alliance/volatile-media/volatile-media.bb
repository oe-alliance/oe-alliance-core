SUMMARY = "Mounts and populates a tmpfs over /media"
MAINTAINER = "PLi team"
inherit allarch

require conf/license/license-gplv2.inc

PV = "2.0"
PR = "r0"

SRC_URI = "file://volatile-media.sh"

S = "${WORKDIR}"

PACKAGES = "${PN}"

FILES_${PN} = "/etc"

do_compile() {
}

do_install() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/volatile-media.sh ${D}${sysconfdir}/init.d
    install -d ${D}${sysconfdir}/rcS.d
    ln -sf ../init.d/volatile-media.sh ${D}${sysconfdir}/rcS.d/S02volatile-media.sh
}
