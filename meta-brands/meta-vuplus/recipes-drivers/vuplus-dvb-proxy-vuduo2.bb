KV = "3.13.5"
PV = "${KV}"
SRCDATE = "20180319"
SRCDATE_PR = "r0"

require vuplus-dvb-proxy.inc

DEPENDS_openvix = "driver-fix"
RDEPENDS_${PN}_openvix = "driver-fix"

DEPENDS_openbh = "driver-fix"
RDEPENDS_${PN}_openbh = "driver-fix"

SRC_URI[md5sum] = "853c3802284e518ec325195d8ae77207"
SRC_URI[sha256sum] = "723c34de47c801dbc3e7f21e60154f41cbea13c64d058ce4b6d589589505166d"
