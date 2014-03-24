KV = "3.9.7"
SRCDATE = "20140323"

RDEPENDS_${PN} += "sogno-mac-check"

SRC_URI[md5sum] = "93fa2409013c4665084ff32a66a411fe"
SRC_URI[sha256sum] = "94818893820f10c98658c25f2ed5bd9bdbdba9b58eeac288620e7c9086127c03"

SRC_URI = "http://pluginvalley.kr/bcm/driver/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require sogno-dvb-modules.inc
