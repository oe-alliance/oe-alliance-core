KV = "5.9.0"
SRCDATE = "20210323"
KOFILES = "brcmstb-${MACHINE} brcmstb-decoder ci si2183 avl6862 avl6261"

require edision-dvb-modules.inc
SRC_URI[md5sum] = "fed32e2e26aa18c972e61c3a75018610"
SRC_URI[sha256sum] = "cc90fa21319a83a69cfab61e2508426b802e5b92766fa5d225ca989079f6b71e"

RRECOMMENDS_${PN} = "linux-firmware-dvb-fe-avl62x1 linux-firmware-dvb-fe-avl68x2"
