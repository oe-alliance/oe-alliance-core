KV = "5.12.0"
SRCDATE = "20210601"
KOFILES = "brcmstb-${MACHINE} brcmstb-decoder ci si2183 avl6862 avl6261"

require edision-dvb-modules.inc
SRC_URI[md5sum] = "3bfde48fdc9f83100aa6729f5fd32942"
SRC_URI[sha256sum] = "0a64d4d32183a2e304c288cfdd8dbb0ef6002815a6bfdf2b98e633f63b03cbbe"

RRECOMMENDS_${PN} = "linux-firmware-dvb-fe-avl62x1 linux-firmware-dvb-fe-avl68x2"
