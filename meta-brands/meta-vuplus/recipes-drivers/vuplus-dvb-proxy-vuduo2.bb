KV = "3.13.5"
PV = "${KV}"
SRCDATE = "20170522"
SRCDATE_PR = "r0"

require vuplus-dvb-proxy.inc

DEPENDS_openvix = "driver-fix"
RDEPENDS_${PN}_openvix = "driver-fix"

DEPENDS_openbh = "driver-fix"
RDEPENDS_${PN}_openbh = "driver-fix"

SRC_URI[md5sum] = "d24185d9fa9afe71668a0d102f380f35"
SRC_URI[sha256sum] = "f241c01e06001c709e54f619171120eecf99bb015d2427aad9a5a0e5e6150eda"
