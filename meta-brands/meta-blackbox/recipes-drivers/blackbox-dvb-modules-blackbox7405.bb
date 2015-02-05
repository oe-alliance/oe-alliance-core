KV = "3.14.21"
SRCDATE = "20150129"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "1aac3f77a5776222ace72801438741ae"
SRC_URI[sha256sum] = "5a8993c83bb8811d2b3d341a0cab5823438a190ddc1ce1e0c4f8abdb09bfee08"

SRC_URI = "http://unibox.to/feeds/eco/development/drivers/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
