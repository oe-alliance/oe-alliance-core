KV = "3.9.7"
SRCDATE = "20140523"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "251a60aaa7df0e96dea559b72dfc4a14"
SRC_URI[sha256sum] = "4b58939a44602e97ae92644b2eae8d4393fda2cdd71bf9302808827912ebfada"

SRC_URI = "http://pluginvalley.kr/bcm/driver/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
