KV = "3.9.7"
SRCDATE = "20140108"

RDEPENDS_${PN} += "sogno-mac-check"

SRC_URI[md5sum] = "568bf8059148a0d82a7c483a6c37110e"
SRC_URI[sha256sum] = "ffcbd1e7a6bd2ff7ee5555999d2ffd9e7c99cf2742200a27ae685c54f11220df"

SRC_URI = "http://pluginvalley.kr/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
