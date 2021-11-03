KV = "5.15.0"
SRCDATE = "20211103"
KOFILES = "brcmstb-${MACHINE} ci si2183 avl6862 avl6261"

require edision-dvb-modules.inc
SRC_URI[md5sum] = "35207225373ea8ae105ba411aa17e8b5"
SRC_URI[sha256sum] = "3e71e7f3533e7cf936663b4ca55b0d10cc0f1025482660e27ffcc849384ea852"

RRECOMMENDS:${PN} = "linux-firmware-dvb-fe-avl62x1 linux-firmware-dvb-fe-avl68x2"
