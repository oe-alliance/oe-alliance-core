KV = "5.9.0"
SRCDATE = "20210323"
KOFILES = "brcmstb-${MACHINE} ci si2183 avl6862 avl6261"

require edision-dvb-modules.inc
SRC_URI[md5sum] = "e993ef9aee789f4574b749688789b1f7"
SRC_URI[sha256sum] = "048dc5262fef25ae4d1bbd2ce5cf211d94212dcc055dbea5c745735b9a275679"

RRECOMMENDS_${PN} = "linux-firmware-dvb-fe-avl62x1 linux-firmware-dvb-fe-avl68x2"
