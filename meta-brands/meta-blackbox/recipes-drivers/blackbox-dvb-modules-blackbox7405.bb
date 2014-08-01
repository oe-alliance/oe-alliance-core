KV = "3.9.7"
SRCDATE = "20140730"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "a0f3902edaaf2d4a5cd92bd9e4772c02"
SRC_URI[sha256sum] = "b1525d31f283c8286c41d172c47d0aee1fc3fab6e55053fb1afebafaa6f3733b"

SRC_URI = "http://pluginvalley.kr/bcm/driver/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
