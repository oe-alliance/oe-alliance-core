KV = "3.13.5"
PV = "${KV}"
SRCDATE = "20180220"
SRCDATE_PR = "r0"

require vuplus-dvb-proxy.inc

DEPENDS_openvix = "driver-fix"
RDEPENDS_${PN}_openvix = "driver-fix"

DEPENDS_openbh = "driver-fix"
RDEPENDS_${PN}_openbh = "driver-fix"

SRC_URI[md5sum] = "6e1793c04ff53c0363efd67d01b7fbbf"
SRC_URI[sha256sum] = "3369a6d077020247907d058252fe8b2ff091bce3f752877188d3e4a5a572c6cc"
