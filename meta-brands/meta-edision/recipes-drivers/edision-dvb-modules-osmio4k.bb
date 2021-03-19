KV = "5.9.0"
SRCDATE = "20210319"
KOFILES = "brcmstb-${MACHINE} brcmstb-decoder ci si2183 avl6862 avl6261"

require edision-dvb-modules.inc
SRC_URI[md5sum] = "1c843265baa74433f1ca492bb63fa94b"
SRC_URI[sha256sum] = "08c0d28c89f3ef694dea0f5bb9255c981226b224e79885eada14e2db3938a4f1"

RRECOMMENDS_${PN} = "linux-firmware-dvb-fe-avl62x1 linux-firmware-dvb-fe-avl68x2"
