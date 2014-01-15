KV = "3.9.7"
SRCDATE = "20140114"

RDEPENDS_${PN} += "sogno-mac-check"

SRC_URI[md5sum] = "1567691c701982b6924fb1c399e8686d"
SRC_URI[sha256sum] = "c40e14e7775b6c8977e422490d693ca315475d37d1a89b3599c16bede954f6b9"

SRC_URI = "http://pluginvalley.kr/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
