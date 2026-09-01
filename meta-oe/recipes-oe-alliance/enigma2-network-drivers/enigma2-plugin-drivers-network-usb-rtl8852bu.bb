SUMMARY = "WiFi devices for Realtek 8832BU/8852BU chipsets."
inherit allarch nospdx

require conf/license/license-gplv2.inc

# Needs a kernel carrying the external auth backport, see
# kernel-patches/wifi/cfg80211-backport-external-auth-4.4.patch
RRECOMMENDS:${PN} = " \
        rtl8852bu \
"

PV = "1.0"
PR = "r0"

ALLOW_EMPTY:${PN} = "1"
