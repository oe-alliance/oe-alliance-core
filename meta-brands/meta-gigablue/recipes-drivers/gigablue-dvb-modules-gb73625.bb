SRCDATE = "20170726"

KV = "4.8.3"

SRC_URI[md5sum] = "a241e3f96efd92bb41fbde989d5f1b74"
SRC_URI[sha256sum] = "f0ad81b89741f7bed7b9da2f306d288059ac08b7759e3552f607a7e0fbcbc675"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM73625-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
