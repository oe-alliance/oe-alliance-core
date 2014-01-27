KV = "3.9.7"
SRCDATE = "20140126"

RDEPENDS_${PN} += "sogno-mac-check"

SRC_URI[md5sum] = "648304b23dcaae1665f93d353f74f7d6"
SRC_URI[sha256sum] = "768aebe0f66f94d9e70549a8a4eeb8410f0c203e23a191bc044fd0e7d903fec2"

SRC_URI = "http://pluginvalley.kr/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
