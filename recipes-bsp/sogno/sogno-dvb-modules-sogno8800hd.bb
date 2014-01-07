KV = "3.9.7"
SRCDATE = "20140107"

RDEPENDS_${PN} += "sogno-mac-check"

SRC_URI[md5sum] = "ef914da429543ce704d40bb525b616a5"
SRC_URI[sha256sum] = "1c61abfa977e0e2ecef68984daa7d1d6228295b33649f031c773e2411964cf4f"

SRC_URI = "http://pluginvalley.kr/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
