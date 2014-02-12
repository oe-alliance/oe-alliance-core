KV = "3.9.7"
SRCDATE = "20140213"

RDEPENDS_${PN} += "sogno-mac-check"

SRC_URI[md5sum] = "9003e80560aae5a76de3bcdcc5ebbbc8"
SRC_URI[sha256sum] = "c3c97ceb4b41f432124ad7b2b2c6422f71d04301b679fcd278c936679995feee"

SRC_URI = "http://pluginvalley.kr/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
