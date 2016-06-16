KV = "3.14.21"
SRCDATE = "20160616"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "0b450b14e2d84edcd36ba9c1512bca88"
SRC_URI[sha256sum] = "f2f26a9c448c08672ebc8fc06478edac73ebd29a42ed11110909617c73412025"

SRC_URI = "http://source.mynonpublic.com/unibox/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
