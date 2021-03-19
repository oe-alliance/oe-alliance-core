KV = "5.9.0"
SRCDATE = "20210319"
KOFILES = "brcmstb-${MACHINE} ci si2183 avl6862 avl6261"

require edision-dvb-modules.inc
SRC_URI[md5sum] = "deec2046200f2ee3d162865ba44b5559"
SRC_URI[sha256sum] = "7bbbeea714ac2b92d1c7cae7ecc45e3c67cbb218945b86fa83a11b780f813b9b"

RRECOMMENDS_${PN} = "linux-firmware-dvb-fe-avl62x1 linux-firmware-dvb-fe-avl68x2"
