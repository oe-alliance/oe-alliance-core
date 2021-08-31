KV = "5.14.0"
SRCDATE = "20210831"
KOFILES = "brcmstb-${MACHINE} brcmstb-decoder ci si2183 avl6862 avl6261"

require edision-dvb-modules.inc
SRC_URI[md5sum] = "fb847aa8c73095d0778eb5c3ee4a43a1"
SRC_URI[sha256sum] = "5a439c1112a6336326e90c982ee9d5e02f5b1d6351107d8fd15bbcb398f599b1"

RRECOMMENDS_${PN} = "linux-firmware-dvb-fe-avl62x1 linux-firmware-dvb-fe-avl68x2"
