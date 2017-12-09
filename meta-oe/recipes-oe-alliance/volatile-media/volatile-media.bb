SUMMARY = "Mounts and populates a tmpfs over /media"
MAINTAINER = "PLi team"
inherit allarch systemd

require conf/license/license-gplv2.inc

PV = "2.0"
PR = "r0"

SRC_URI = "file://volatile-media.sh \
           file://media.mount \
           file://media.service \
          "

S = "${WORKDIR}"

PACKAGES = "${PN}"

FILES_${PN} = "/etc"

SYSTEMD_SERVICE_${PN} = "volatile-media.service"

do_compile() {
}

do_install() {
	if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
		install -d ${D}${systemd_unitdir}/system
		install -m 0644 ${WORKDIR}/media.service ${D}${systemd_unitdir}/system/volatile-media.service
	else
		install -d ${D}${sysconfdir}/init.d
		install -m 0755 ${WORKDIR}/volatile-media.sh ${D}${sysconfdir}/init.d
		install -d ${D}${sysconfdir}/rcS.d
		ln -sf ../init.d/volatile-media.sh ${D}${sysconfdir}/rcS.d/S02volatile-media.sh
	fi
}
