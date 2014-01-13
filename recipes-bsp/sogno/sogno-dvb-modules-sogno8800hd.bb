KV = "3.9.7"
SRCDATE = "20140111"

RDEPENDS_${PN} += "sogno-mac-check"

SRC_URI[md5sum] = "15127acd3cde8e91092db73323e983a0"
SRC_URI[sha256sum] = "10db6d3857b4d5def6fb21fcdd0b448e8061208f65f7df5f493fe9e0fde50fe9"

SRC_URI = "http://pluginvalley.kr/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
