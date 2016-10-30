KV = "4.4.8"
GCC = "5.3.0"
SRCDATE = "20161027"

SRC_URI = "http://www.xtrendet.net/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "7f0ab683fcad263470589da97a8647fb"
SRC_URI[sha256sum] = "e232343a2731d47d1fe616b0d9220ba1761898ea3ad433c032dcda6af519738d"
