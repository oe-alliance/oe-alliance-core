KV = "5.15.0"
SRCDATE = "20211228"
KOFILES = "brcmstb-${MACHINE} brcmstb-decoder ci si2183 avl6862 avl6261"

require edision-dvb-modules.inc
SRC_URI[md5sum] = "d1e7d956b1b4a8b5a13754ed4e24f67f"
SRC_URI[sha256sum] = "7e2d3d31f1cf8459d2a5dbb3be547c5e59b55c390144f51f6c08649b491687ca"

RRECOMMENDS_${PN} = "linux-firmware-dvb-fe-avl62x1 linux-firmware-dvb-fe-avl68x2"
