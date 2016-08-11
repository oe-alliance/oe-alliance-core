KV = "4.4.8"
GCC = "4.9.1"
SRCDATE = "20160715"

SRC_URI = "http://www.xtrendet.net/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "78922ce589d4ab5587fa32fca4693236"
SRC_URI[sha256sum] = "f90a171812f4e114887d3f9ca284ff127af67f287ab7f074d6c8986a61b6a341"
