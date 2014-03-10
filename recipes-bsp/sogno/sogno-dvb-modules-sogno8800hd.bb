KV = "3.9.7"
SRCDATE = "20140310"

RDEPENDS_${PN} += "sogno-mac-check"

SRC_URI[md5sum] = "24bdd71bb2c0e708c66dbb58173decde"
SRC_URI[sha256sum] = "e1950e44323c6bf12ef3fd9e3b784ffe5242f0171fb5734c4fa8e870d067977f"

SRC_URI = "http://pluginvalley.kr/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
