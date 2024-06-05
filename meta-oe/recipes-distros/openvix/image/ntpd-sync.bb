SUMMARY = "Sync with NTP on network up"
PRIORITY = "required"
MAINTAINER = "openvix team"

require conf/license/license-gplv2.inc
inherit allarch

PV = "${IMAGE_VERSION}"
PR = "r1"

NTPD_SYNC_LOC := "${THISDIR}/${PN}"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
    install -d ${D}${bindir}
    install -m 755 ${NTPD_SYNC_LOC}/ntpdate-sync ${D}${bindir}/ntpdate-sync
    install -d ${D}${sysconfdir}/network/if-up.d
    ln -sf ${bindir}/ntpdate-sync ${D}${sysconfdir}/network/if-up.d/ntpdate-sync
}
