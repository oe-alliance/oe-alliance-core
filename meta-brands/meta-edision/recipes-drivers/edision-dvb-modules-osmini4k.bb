KV = "5.12.0"
SRCDATE = "20210601"
KOFILES = "brcmstb-${MACHINE} ci si2183 avl6862 avl6261"

require edision-dvb-modules.inc
SRC_URI[md5sum] = "a1575d1eb7c145ccf73ffa382b6efe99"
SRC_URI[sha256sum] = "a157e88b0074ded60101a2781ac845e178e0aa07019d60165a1b878fbfb06d87"

RRECOMMENDS_${PN} = "linux-firmware-dvb-fe-avl62x1 linux-firmware-dvb-fe-avl68x2"
