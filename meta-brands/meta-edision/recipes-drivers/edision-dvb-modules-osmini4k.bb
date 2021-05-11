KV = "5.12.0"
SRCDATE = "20210511"
KOFILES = "brcmstb-${MACHINE} ci si2183 avl6862 avl6261"

require edision-dvb-modules.inc
SRC_URI[md5sum] = "900d659252e9fd6ed295050e0d71cb37"
SRC_URI[sha256sum] = "6418a213014b4ff2d8203608f626a0465dcaa3a5fbd9cf88e1ac3708b2d7b66b"

RRECOMMENDS_${PN} = "linux-firmware-dvb-fe-avl62x1 linux-firmware-dvb-fe-avl68x2"
