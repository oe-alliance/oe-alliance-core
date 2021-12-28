KV = "5.15.0"
SRCDATE = "20211228"
KOFILES = "brcmstb-${MACHINE} ci si2183 avl6862 avl6261"

require edision-dvb-modules.inc
SRC_URI[md5sum] = "9a8076a57ede632e0d6ecb7b5c08b366"
SRC_URI[sha256sum] = "d3402e5ad18c9cd21ab6b4f6a4fb03ed5ae159896229440bd17844563e75d43a"

RRECOMMENDS:${PN} = "linux-firmware-dvb-fe-avl62x1 linux-firmware-dvb-fe-avl68x2"
