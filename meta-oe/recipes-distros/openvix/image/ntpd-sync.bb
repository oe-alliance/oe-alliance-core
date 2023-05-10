SUMMARY = "Sync with NTP on network up"
PRIORITY = "required"
MAINTAINER = "openvix team"

require conf/license/license-gplv2.inc
inherit allarch

PV = "${IMAGE_VERSION}"
PR = "r0"

NTPD_SYNC_LOC := "${THISDIR}/${PN}"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 755 ${NTPD_SYNC_LOC}/ntpd-sync ${D}${bindir}/ntpd-sync
    install -d ${D}${sysconfdir}/network/if-up.d
    ln -sf ${bindir}/ntpd-sync ${D}${sysconfdir}/network/if-up.d/ntpd-sync
}
