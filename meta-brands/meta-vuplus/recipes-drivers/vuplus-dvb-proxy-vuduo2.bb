KV = "3.13.5"
PV = "${KV}"
SRCDATE = "20171121"
SRCDATE_PR = "r0"

require vuplus-dvb-proxy.inc

DEPENDS_openvix = "driver-fix"
RDEPENDS_${PN}_openvix = "driver-fix"

DEPENDS_openbh = "driver-fix"
RDEPENDS_${PN}_openbh = "driver-fix"

SRC_URI[md5sum] = "14fd3c5caa88ac75a7b5d6e202a11968"
SRC_URI[sha256sum] = "cd516a0ba9f74a0106fc6e6ec7a81e2c24dbfa810073b6ecc7c073487c86f8c0"
