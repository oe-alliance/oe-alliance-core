KV = "3.13.5"
PV = "${KV}"
SRCDATE = "20170822"
SRCDATE_PR = "r0"

require vuplus-dvb-proxy.inc

DEPENDS_openvix = "driver-fix"
RDEPENDS_${PN}_openvix = "driver-fix"

DEPENDS_openbh = "driver-fix"
RDEPENDS_${PN}_openbh = "driver-fix"

SRC_URI[md5sum] = "fc6995531e18c7f31b5a4d7de376161e"
SRC_URI[sha256sum] = "2dbe00127103771ff0ba306fa2f8f72ef048da542d69aafe6553e9c0b5c7e7e4"
