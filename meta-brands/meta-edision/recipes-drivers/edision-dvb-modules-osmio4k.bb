KV = "5.14.0"
SRCDATE = "20211102"
KOFILES = "brcmstb-${MACHINE} brcmstb-decoder ci si2183 avl6862 avl6261"

require edision-dvb-modules.inc
SRC_URI[md5sum] = "8bff642020d4495a21b0505054152411"
SRC_URI[sha256sum] = "0761657fde15c28e38c79d68449d114ee1d0d01ba90bb28d10f7ab9c05327941"

RRECOMMENDS:${PN} = "linux-firmware-dvb-fe-avl62x1 linux-firmware-dvb-fe-avl68x2"
