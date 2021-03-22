KV = "5.9.0"
SRCDATE = "20210319"
KOFILES = "brcmstb-${MACHINE} brcmstb-decoder ci si2183 avl6862 avl6261"

require edision-dvb-modules.inc
SRC_URI[md5sum] = "45c902e27726c6378c2cb880be31dd60"
SRC_URI[sha256sum] = "5dbbdb37d59d1165bc851bfcea5193dd9b3b9c0eb039f2bc11711a1db97872b6"

RRECOMMENDS_${PN} = "linux-firmware-dvb-fe-avl62x1 linux-firmware-dvb-fe-avl68x2"
