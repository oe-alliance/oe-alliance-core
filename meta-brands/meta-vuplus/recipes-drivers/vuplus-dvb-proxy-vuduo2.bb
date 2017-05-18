KV = "3.13.5"
PV = "${KV}"
SRCDATE = "20161228"
SRCDATE_PR = "r0"

require vuplus-dvb-proxy.inc

DEPENDS_openvix = "driver-fix"
RDEPENDS_${PN}_openvix = "driver-fix"

DEPENDS_openbh = "driver-fix"
RDEPENDS_${PN}_openbh = "driver-fix"

SRC_URI[md5sum] = "152a2fe599f54a5c498cbeceb3af635a"
SRC_URI[sha256sum] = "f7dce0cf6dd059a0aea3529cfd2702967edd0bc62f123f72bd6bfcc00ac40f01"
