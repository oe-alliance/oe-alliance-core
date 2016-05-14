KV = "3.14.21"
SRCDATE = "20160421"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "93c631e6748c985998eab0094a2ccdd1"
SRC_URI[sha256sum] = "ae31110c316f31ecc048598801aee9e9f7caeaa9aa3a3c2c662f48a445c8c311"

SRC_URI = "http://unibox.to/feeds/eco/development/drivers/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
