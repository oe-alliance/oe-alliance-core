KV = "4.4.8"
GCC = "5.3.0"
SRCDATE = "20160715"

SRC_URI = "http://www.xtrendet.net/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "4078d4e7c500ae96d837613c66ea28ef"
SRC_URI[sha256sum] = "b9c08e205320f2823c7867104debdfc845f7d05ccf6802872dce6bf6bcf7f0a8"
