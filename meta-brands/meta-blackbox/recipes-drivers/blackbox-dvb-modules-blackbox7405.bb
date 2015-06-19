KV = "3.14.21"
SRCDATE = "20150617"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "70abc7a387109923b9f0398bf9dd169d"
SRC_URI[sha256sum] = "32beee2e69900237c6f0b05bade7339fb02d14bb2eafe636822074e3582998f2"

SRC_URI = "http://unibox.to/feeds/eco/development/drivers/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
