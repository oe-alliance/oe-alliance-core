KV = "5.13.0"
SRCDATE = "20210629"
KOFILES = "brcmstb-${MACHINE} ci si2183 avl6862 avl6261"

require edision-dvb-modules.inc
SRC_URI[md5sum] = "953bf8a321ed5cb375b8b646a86c74d9"
SRC_URI[sha256sum] = "da221c6d0ea6bc0aae040c74a471e53bd97fad4d11cc0b17853daf31a5de4537"

RRECOMMENDS_${PN} = "linux-firmware-dvb-fe-avl62x1 linux-firmware-dvb-fe-avl68x2"
