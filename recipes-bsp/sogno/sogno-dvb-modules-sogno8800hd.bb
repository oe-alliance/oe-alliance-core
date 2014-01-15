KV = "3.9.7"
SRCDATE = "20140115"

RDEPENDS_${PN} += "sogno-mac-check"

SRC_URI[md5sum] = "01d5a94f4f77a569a4a428c609c38f67"
SRC_URI[sha256sum] = "bfe6b6cf7cb343aed2d080da383ce122e6bb9f989bce8819e58162d5b97dd840"

SRC_URI = "http://pluginvalley.kr/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
