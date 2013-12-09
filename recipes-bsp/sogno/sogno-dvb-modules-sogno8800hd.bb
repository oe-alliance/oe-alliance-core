KV = "3.9.7"
SRCDATE = "20131209"

SRC_URI[md5sum] = "c734d6b534153cce58cde84b07356200"
SRC_URI[sha256sum] = "4e686f276a977eecfde6b59b9722e293c3dea0736c19879b964cbce983b77d23"

SRC_URI = "http://whitebox.host.sk/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
