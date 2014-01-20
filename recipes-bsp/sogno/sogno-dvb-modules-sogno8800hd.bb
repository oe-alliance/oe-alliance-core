KV = "3.9.7"
SRCDATE = "20140117"

RDEPENDS_${PN} += "sogno-mac-check"

SRC_URI[md5sum] = "bf32b2bde7776af5332d28a78ffeb928"
SRC_URI[sha256sum] = "f6589a619c1a137208456e6c43c6398046db590213c5d50748cc16e6182591cd"

SRC_URI = "http://pluginvalley.kr/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
