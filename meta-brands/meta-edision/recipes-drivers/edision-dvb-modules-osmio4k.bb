KV = "5.12.0"
SRCDATE = "20210426"
KOFILES = "brcmstb-${MACHINE} brcmstb-decoder ci si2183 avl6862 avl6261"

require edision-dvb-modules.inc
SRC_URI[md5sum] = "58ad95fa2e2920c04a4466dac569bfd8"
SRC_URI[sha256sum] = "27859261ea23fcb13ea61f4fe0ea0c37e1e75168bde88e2bee5c365534480304"

RRECOMMENDS_${PN} = "linux-firmware-dvb-fe-avl62x1 linux-firmware-dvb-fe-avl68x2"
