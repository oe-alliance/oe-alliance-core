KV = "3.9.7"
SRCDATE = "20140109"

RDEPENDS_${PN} += "sogno-mac-check"

SRC_URI[md5sum] = "c5756a37152dcfe6ce06dc09f1926f87"
SRC_URI[sha256sum] = "7516505d6c56aa72d7a318aa2787b40a6988ca2834dc96b103d629056e5a1529"

SRC_URI = "http://pluginvalley.kr/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
