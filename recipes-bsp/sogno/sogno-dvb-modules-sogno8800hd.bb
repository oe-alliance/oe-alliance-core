KV = "3.9.7"
SRCDATE = "20140215"

RDEPENDS_${PN} += "sogno-mac-check"

SRC_URI[md5sum] = "562cf9047e63e32f1db5cacf9f97fe94"
SRC_URI[sha256sum] = "f07965279b9df2dd7c1886ee2271779fae9dd619f2f4e06bd4c15cf4569eb098"

SRC_URI = "http://pluginvalley.kr/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
