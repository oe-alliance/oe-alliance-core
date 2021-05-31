KV = "5.12.0"
SRCDATE = "20210524"
KOFILES = "brcmstb-${MACHINE} brcmstb-decoder ci si2183 avl6862 avl6261"

require edision-dvb-modules.inc
SRC_URI[md5sum] = "8c4fbfbb04cef9862ce9690b564b4576"
SRC_URI[sha256sum] = "36d2a6277db8580ea2a68537d7e3eb2ed88783eea1c823c86b1eb33074dddc05"

RRECOMMENDS_${PN} = "linux-firmware-dvb-fe-avl62x1 linux-firmware-dvb-fe-avl68x2"
