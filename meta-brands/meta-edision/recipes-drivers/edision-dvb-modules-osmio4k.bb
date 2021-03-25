KV = "5.9.0"
SRCDATE = "20210323"
KOFILES = "brcmstb-${MACHINE} brcmstb-decoder ci si2183 avl6862 avl6261"

require edision-dvb-modules.inc
SRC_URI[md5sum] = "582a303938ea982929f36c873b7b030f"
SRC_URI[sha256sum] = "8c43852d6b0d411b987205ef6e137f7fca64e48cf738c4b5a20e8784c6cd2361"

RRECOMMENDS_${PN} = "linux-firmware-dvb-fe-avl62x1 linux-firmware-dvb-fe-avl68x2"
