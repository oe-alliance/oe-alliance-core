KV = "3.9.7"
SRCDATE = "20140414"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "f0fc78498d73be8b10399cc933fb0cde"
SRC_URI[sha256sum] = "e28a45925c287130486d865573119d55f5202ee92785affa5ad273ea2cab562d"

SRC_URI = "http://pluginvalley.kr/bcm/driver/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
