KV = "3.9.7"
SRCDATE = "20140116"

RDEPENDS_${PN} += "sogno-mac-check"

SRC_URI[md5sum] = "cab01124beb6dd576d6912dc9fb6e13c"
SRC_URI[sha256sum] = "e638027dcfe088ed845b461f9f35d9db3c4be03c8de9f6ca52596cf9920bcb35"

SRC_URI = "http://pluginvalley.kr/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
