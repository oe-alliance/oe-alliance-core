KV = "5.15.0"
SRCDATE = "20211103"
KOFILES = "brcmstb-${MACHINE} brcmstb-decoder ci si2183 avl6862 avl6261"

require edision-dvb-modules.inc
SRC_URI[md5sum] = "29a6e6be629e9ba71ba4f924986073ab"
SRC_URI[sha256sum] = "569c828b37c952b1bca752a139d0073545335f73a00e4033433b33ca18b4fc71"

RRECOMMENDS:${PN} = "linux-firmware-dvb-fe-avl62x1 linux-firmware-dvb-fe-avl68x2"
