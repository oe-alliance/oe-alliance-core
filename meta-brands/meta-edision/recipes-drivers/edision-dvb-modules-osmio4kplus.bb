KV = "5.14.0"
SRCDATE = "20211102"
KOFILES = "brcmstb-${MACHINE} brcmstb-decoder ci si2183 avl6862 avl6261"

require edision-dvb-modules.inc
SRC_URI[md5sum] = "9e86e479e491b72df4bbb2772da45744"
SRC_URI[sha256sum] = "97e8fd75bd85fb04d3407a6c40eea677a641a5f91b5149d6d21b1b0f361aa6c1"

RRECOMMENDS_${PN} = "linux-firmware-dvb-fe-avl62x1 linux-firmware-dvb-fe-avl68x2"
