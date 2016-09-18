KV = "3.14.21"
SRCDATE = "20160913"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "af2b326d3753c5ba92474f0698e28cfd"
SRC_URI[sha256sum] = "a87c177e2e6012e5785c0aa3ad832c46a0646207176d039ab190a60adb9d4fe0"

SRC_URI = "http://source.mynonpublic.com/unibox/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
