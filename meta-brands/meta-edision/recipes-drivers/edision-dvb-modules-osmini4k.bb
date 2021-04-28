KV = "5.12.0"
SRCDATE = "20210428"
KOFILES = "brcmstb-${MACHINE} ci si2183 avl6862 avl6261"

require edision-dvb-modules.inc
SRC_URI[md5sum] = "54c5f9fb73ae13fc508f11c762b0f3f4"
SRC_URI[sha256sum] = "1b4f26604193f460e5159db676965597ae389b328a992922df91d03b7962d20e"

RRECOMMENDS_${PN} = "linux-firmware-dvb-fe-avl62x1 linux-firmware-dvb-fe-avl68x2"
