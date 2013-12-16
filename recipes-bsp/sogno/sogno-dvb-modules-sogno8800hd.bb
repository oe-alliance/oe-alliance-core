KV = "3.9.7"
SRCDATE = "20131216"

SRC_URI[md5sum] = "c4ceb59907da6af4918c418ac5015b15"
SRC_URI[sha256sum] = "6619e6f5503b48f408463013fe409c2ccbdd8034434bd817a645e8756973fd62"

SRC_URI = "http://whitebox.host.sk/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
