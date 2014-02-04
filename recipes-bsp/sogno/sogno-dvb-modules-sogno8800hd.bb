KV = "3.9.7"
SRCDATE = "20140202"

RDEPENDS_${PN} += "sogno-mac-check"

SRC_URI[md5sum] = "399adb7c24d5777af8a002017b477344"
SRC_URI[sha256sum] = "8863987ef0376f143e9fea7635db22b0ddca7cffdd03ca4aef62a38f6e0d693d"

SRC_URI = "http://pluginvalley.kr/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
