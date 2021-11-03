KV = "5.15.0"
SRCDATE = "20211103"
KOFILES = "brcmstb-${MACHINE} brcmstb-decoder ci si2183 avl6862 avl6261"

require edision-dvb-modules.inc
SRC_URI[md5sum] = "5b3f00987f5a83b7b9e09f91d64460d6"
SRC_URI[sha256sum] = "fe1fc3438a8f4dd087dc5efbfd21aebc04f825c07e6fea136f2bfcee87df943b"

RRECOMMENDS_${PN} = "linux-firmware-dvb-fe-avl62x1 linux-firmware-dvb-fe-avl68x2"
