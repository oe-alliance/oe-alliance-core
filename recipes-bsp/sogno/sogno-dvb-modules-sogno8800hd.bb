KV = "3.9.7"
SRCDATE = "20140225"

RDEPENDS_${PN} += "sogno-mac-check"

SRC_URI[md5sum] = "60009449e23d7c3f3efa5b5b08c509a4"
SRC_URI[sha256sum] = "74456add6b2c8e5d5d75b4af2bb639bbf93eef47f185f723ea9b7043fe1069fa"

SRC_URI = "http://pluginvalley.kr/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
