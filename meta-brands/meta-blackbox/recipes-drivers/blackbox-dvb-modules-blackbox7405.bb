KV = "3.14.21"
SRCDATE = "20150721"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "ab0f5dd01062e434bda08354f3b2cc6f"
SRC_URI[sha256sum] = "d560a535101c8439eda39bbcd917e80eea4fabd544b397788c607d728d21d3e2"

#SRC_URI = "http://unibox.to/feeds/eco/development/drivers/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"
SRC_URI = "http://source.mynonpublic.com/unibox/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
