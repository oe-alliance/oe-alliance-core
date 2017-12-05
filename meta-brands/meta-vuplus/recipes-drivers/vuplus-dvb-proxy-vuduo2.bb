KV = "3.13.5"
PV = "${KV}"
SRCDATE = "20171204"
SRCDATE_PR = "r0"

require vuplus-dvb-proxy.inc

DEPENDS_openvix = "driver-fix"
RDEPENDS_${PN}_openvix = "driver-fix"

DEPENDS_openbh = "driver-fix"
RDEPENDS_${PN}_openbh = "driver-fix"

SRC_URI[md5sum] = "5fed2b504efa69b9cb4f370fbb0b262e"
SRC_URI[sha256sum] = "cf9d0090200a2ab220234ef1fc94a430adcb6a78dab8012d8cb5265d2a2d9d5a"
