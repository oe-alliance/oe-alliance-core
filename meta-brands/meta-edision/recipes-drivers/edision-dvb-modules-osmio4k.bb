KV = "5.15.0"
SRCDATE = "20211228"
KOFILES = "brcmstb-${MACHINE} brcmstb-decoder ci si2183 avl6862 avl6261"

require edision-dvb-modules.inc
SRC_URI[md5sum] = "ab44e39bface80851e34edbd073418ea"
SRC_URI[sha256sum] = "f958cf974ba09cfccb2bf2013d48898732ad84d8863da9f5d758a3a7f844165f"

RRECOMMENDS_${PN} = "linux-firmware-dvb-fe-avl62x1 linux-firmware-dvb-fe-avl68x2"
