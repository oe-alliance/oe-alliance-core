KV = "3.14.21"
SRCDATE = "20161207"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "193181a1b759e20bd0cec03bbc41c204"
SRC_URI[sha256sum] = "0a136c5dec1810c59a60c879bf90314a5568fe0460a18b2a08c80593959ccae5"

SRC_URI = "http://source.mynonpublic.com/unibox/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
