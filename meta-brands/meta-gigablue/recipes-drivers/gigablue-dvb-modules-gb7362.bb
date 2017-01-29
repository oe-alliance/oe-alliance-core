SRCDATE = "20170118"

KV = "4.8.3"

SRC_URI[md5sum] = "18cba7d2acf662120ae63262dbe679ae"
SRC_URI[sha256sum] = "e0a19b28ccaba2ce253172d0ed3d680fc23802ce7f34305723097c5683530335"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7362-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
