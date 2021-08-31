KV = "5.14.0"
SRCDATE = "20210831"
KOFILES = "brcmstb-${MACHINE} ci si2183 avl6862 avl6261"

require edision-dvb-modules.inc
SRC_URI[md5sum] = "0f802fe095b725173ea825af84d440f4"
SRC_URI[sha256sum] = "410fc2ddad53f72a42c024a3a8d335126e1bbad8811417afa1b18f58fa7cdb6e"

RRECOMMENDS_${PN} = "linux-firmware-dvb-fe-avl62x1 linux-firmware-dvb-fe-avl68x2"
