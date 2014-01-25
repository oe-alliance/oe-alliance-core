KV = "3.9.7"
SRCDATE = "20140123"

RDEPENDS_${PN} += "sogno-mac-check"

SRC_URI[md5sum] = "bf888603b8d913706f9e80506fd8a8d7"
SRC_URI[sha256sum] = "bf379656e09e3369e33b78ff828e1ac9ad2fc92d05b48bcaa6eb823a70db1114"

SRC_URI = "http://pluginvalley.kr/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
