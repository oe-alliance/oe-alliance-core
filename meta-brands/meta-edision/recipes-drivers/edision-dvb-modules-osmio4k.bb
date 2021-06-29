KV = "5.13.0"
SRCDATE = "20210629"
KOFILES = "brcmstb-${MACHINE} brcmstb-decoder ci si2183 avl6862 avl6261"

require edision-dvb-modules.inc
SRC_URI[md5sum] = "edfed9ccb32f1ff5c39079133c9ef09c"
SRC_URI[sha256sum] = "ccf239dfd717059cdb08599a8a3e4d1a79eacf90a2072fd9f3203a6dd3ecceee"

RRECOMMENDS_${PN} = "linux-firmware-dvb-fe-avl62x1 linux-firmware-dvb-fe-avl68x2"
