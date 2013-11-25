SRCDATE = "20131117"

KV = "3.8.7"

SRC_URI[md5sum] = "13c495a983104750189346dac2e448a0"
SRC_URI[sha256sum] = "8474edcf2a560586859cd60efc8184628d1e5abcb03e816870473ac0ab842858"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-gb800xxplus-drivers-${KV}-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

