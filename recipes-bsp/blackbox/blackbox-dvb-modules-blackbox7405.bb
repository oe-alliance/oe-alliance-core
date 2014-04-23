KV = "3.9.7"
SRCDATE = "20140418"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "3859e45600f40a09c2db78c551f19f17"
SRC_URI[sha256sum] = "cd56a34d614b4ddb21dd35b5e6d7bbd32478c0df409b4dba4d244e536215d276"

SRC_URI = "http://pluginvalley.kr/bcm/driver/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
