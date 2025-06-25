SUMMARY = "Sync with NTP on network up"
PRIORITY = "required"
MAINTAINER = "openvix team"

require conf/license/license-gplv2.inc
inherit allarch update-rc.d

PV = "${IMAGE_VERSION}"
PR = "r3"

NTPD_SYNC_LOC := "${THISDIR}/${PN}"

S = "${UNPACKDIR}"

# 95 seems to be the earliest useful point for some Wifi systems
#
INITSCRIPT_NAME = "ntp-setdate"
INITSCRIPT_PARAMS = "start 95 3 ."

do_install() {
    install -d ${D}${bindir}
    install -m 755 ${NTPD_SYNC_LOC}/ntpdate-sync ${D}${bindir}/ntpdate-sync

    install -d ${D}${sysconfdir}/init.d
    install -m 755 ${NTPD_SYNC_LOC}/${INITSCRIPT_NAME} ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
}

# Remove /etc/network/if-up.d/wpa-supplicant link, if it is there
# It is no longer needed (nor wanted)
# This can be removed once OpenVix moves beyond OE 5.5.1 to a version
# that requires a reflash, as the file will no longer be there.
#
pkg_postinst:${PN}() {
#!/bin/sh
#
# Remove this iff still there.
rm -f "$D/etc/network/if-up.d/ntpdate-sync"
}
