KV = "3.13.5"
PV = "${KV}"
SRCDATE = "20171107"
SRCDATE_PR = "r0"

require vuplus-dvb-proxy.inc

DEPENDS_openvix = "driver-fix"
RDEPENDS_${PN}_openvix = "driver-fix"

DEPENDS_openbh = "driver-fix"
RDEPENDS_${PN}_openbh = "driver-fix"

SRC_URI[md5sum] = "33e99a6d3ba5ce8c2e0dac7d89d02124"
SRC_URI[sha256sum] = "5aacb5d96b79c3aeec8b5efe49eb66972ac91fbf8ed2f959e78cdd466e27efd2"
