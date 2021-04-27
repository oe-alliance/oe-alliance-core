KV = "5.12.0"
SRCDATE = "20210426"
KOFILES = "brcmstb-${MACHINE} ci si2183 avl6862 avl6261"

require edision-dvb-modules.inc
SRC_URI[md5sum] = "fce0c6f695567d4c635aef6afb80f87d"
SRC_URI[sha256sum] = "1de18804675a035cd014decf0279edfa52d7ce545a6f0e10645fc71ebb32bbf7"

RRECOMMENDS_${PN} = "linux-firmware-dvb-fe-avl62x1 linux-firmware-dvb-fe-avl68x2"
