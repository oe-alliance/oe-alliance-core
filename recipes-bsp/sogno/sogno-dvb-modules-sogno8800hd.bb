KV = "3.9.7"
SRCDATE = "20140315"

RDEPENDS_${PN} += "sogno-mac-check"

SRC_URI[md5sum] = "4b12b93a64eb87e59a56c8b0b7e04dd6"
SRC_URI[sha256sum] = "7ae92f825b15497d579bb3597fd55e977fa108482fcd69d25f70abd32ac68d4c"

SRC_URI = "http://pluginvalley.kr/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
