KV = "3.9.7"
SRCDATE = "20140219"

RDEPENDS_${PN} += "sogno-mac-check"

SRC_URI[md5sum] = "616c749408b42f8194391a15bcbd644e"
SRC_URI[sha256sum] = "93fc6b5ce4e05a27346554534412ab934cfbe266916a9c9b9efea16d797ed901"

SRC_URI = "http://pluginvalley.kr/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
