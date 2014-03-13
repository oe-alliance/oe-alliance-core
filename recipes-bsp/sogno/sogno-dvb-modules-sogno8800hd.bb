KV = "3.9.7"
SRCDATE = "20140311"

RDEPENDS_${PN} += "sogno-mac-check"

SRC_URI[md5sum] = "eaed36006234e985f8ca6f4d424270ef"
SRC_URI[sha256sum] = "0b9e0abbdf616ce13d4f465f473b31e9d4cdd36f1fa13b493a5a3f533505ba2d"

SRC_URI = "http://pluginvalley.kr/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
