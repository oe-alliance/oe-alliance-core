KV = "3.14.21"
SRCDATE = "20150509"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "fa81c47276afa71c6988583125f9b4f9"
SRC_URI[sha256sum] = "478d6ece6fcf8535da7fc9ac2f80907b7e9124585978b6e33aa2fc8910353289"

SRC_URI = "http://unibox.to/feeds/eco/development/drivers/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
