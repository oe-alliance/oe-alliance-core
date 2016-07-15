KV = "4.4.8"
GCC = "5.3.0"
SRCDATE = "20160715"

SRC_URI = "http://www.xtrendet.net/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "82aa2160164b87982a3e75a579367a63"
SRC_URI[sha256sum] = "24c336c585fa62ac0eb62b63ac82a781925f14c91885ce8d785233af0e18f8c1"
