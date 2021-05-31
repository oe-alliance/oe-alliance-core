KV = "5.12.0"
SRCDATE = "20210524"
KOFILES = "brcmstb-${MACHINE} brcmstb-decoder ci si2183 avl6862 avl6261"

require edision-dvb-modules.inc
SRC_URI[md5sum] = "00443531c358dea9e8a3c15ecc46d46c"
SRC_URI[sha256sum] = "c2049fe7829d507aa2af178373a8997c53322437a113827256a0694f0ce1b1ca"

RRECOMMENDS_${PN} = "linux-firmware-dvb-fe-avl62x1 linux-firmware-dvb-fe-avl68x2"
