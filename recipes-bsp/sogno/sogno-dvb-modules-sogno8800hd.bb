KV = "3.9.7"
SRCDATE = "20140214"

RDEPENDS_${PN} += "sogno-mac-check"

SRC_URI[md5sum] = "43f895edbbb243e0f37a417bbd43cc04"
SRC_URI[sha256sum] = "3dc3cd19bb9b6a38086a6ef6ddc415b12ed64f7492abe4ac9e9807f64752ed1e"

SRC_URI = "http://pluginvalley.kr/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
