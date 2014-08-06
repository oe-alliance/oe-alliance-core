SUMMARY = "User space daemon for extended IEEE 802.11 management"

require hostap-daemon-0.7.inc

PR = "r3"

DEFAULT_PREFERENCE = "-1"

SRC_URI = "http://hostap.epitest.fi/releases/hostapd-${PV}.tar.gz \
    file://defconfig \
    file://init"
SRC_URI[md5sum] = "91a7c8d0f090b7104152d3455a84c112"
SRC_URI[sha256sum] = "31eb2781f37e1a4c27969d1594f8019c0ca87779349c099ab812833289961567"

S = "${WORKDIR}/hostapd-${PV}/hostapd"


