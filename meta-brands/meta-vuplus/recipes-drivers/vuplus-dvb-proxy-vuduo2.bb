KV = "3.13.5"
PV = "${KV}"
SRCDATE = "20190429"
SRCDATE_PR = "r0"

require vuplus-dvb-proxy.inc

DEPENDS:openvix = "driver-fix"
RDEPENDS:${PN}:openvix = "driver-fix"

DEPENDS:openbh = "driver-fix"
RDEPENDS:${PN}:openbh = "driver-fix"

SRC_URI[md5sum] = "42b7bcba72ea9313ba80e8dd769c5c15"
SRC_URI[sha256sum] = "2cbbf91b7659de762182e8a6ee7b7ab0f6389a67fec13df21fb508dce92a7fdf"
