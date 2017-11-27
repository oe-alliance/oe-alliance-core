KV = "3.13.5"
PV = "${KV}"
SRCDATE = "20171127"
SRCDATE_PR = "r0"

require vuplus-dvb-proxy.inc

DEPENDS_openvix = "driver-fix"
RDEPENDS_${PN}_openvix = "driver-fix"

DEPENDS_openbh = "driver-fix"
RDEPENDS_${PN}_openbh = "driver-fix"

SRC_URI[md5sum] = "fbb97498a56698c4f9facb4cbd6da8b8"
SRC_URI[sha256sum] = "843e85f0298bbe824ec0377aae5b2e18107727e8ab6b0bf56c45f8b98df8e35b"
