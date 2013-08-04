DESCRIPTION = "Mounts and populates a tmpfs over /media"
MAINTAINER = "PLi team"

require conf/license/openpli-gplv2.inc

PV = "1.0"
PR = "r2"

SRC_URI = "file://volatile-media.sh"

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

inherit allarch
