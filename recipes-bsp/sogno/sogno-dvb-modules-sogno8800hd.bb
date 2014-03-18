KV = "3.9.7"
SRCDATE = "20140318"

RDEPENDS_${PN} += "sogno-mac-check"

SRC_URI[md5sum] = "491620c2c9406e374f35451dafca3edc"
SRC_URI[sha256sum] = "8109d0ea9ba47cf595467b7ca098f976b030322e11112b9b54a292f6c732662b"

SRC_URI = "http://pluginvalley.kr/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
