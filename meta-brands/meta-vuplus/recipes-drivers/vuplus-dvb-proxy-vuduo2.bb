KV = "3.13.5"
PV = "${KV}"
SRCDATE = "20171128"
SRCDATE_PR = "r0"

require vuplus-dvb-proxy.inc

DEPENDS_openvix = "driver-fix"
RDEPENDS_${PN}_openvix = "driver-fix"

DEPENDS_openbh = "driver-fix"
RDEPENDS_${PN}_openbh = "driver-fix"

SRC_URI[md5sum] = "47392629f063e9c7d295b0763b3cefb6"
SRC_URI[sha256sum] = "798c77122cc98704336a59d4fb7ae19b7a42f61268194c8d6d9632566f6838e9"
