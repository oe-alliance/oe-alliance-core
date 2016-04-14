KV = "4.1.21"
GCCREV = "5.3.0"
SRCDATE = "20160407"

SRC_URI = "http://source.mynonpublic.com/protek/${MACHINE}-drivers-${KV}-${GCCREV}-${SRCDATE}.zip"
require protek-dvb-modules.inc

SRC_URI[md5sum] = "fbeb8783db299a4dfada0e784c49e598"
SRC_URI[sha256sum] = "4936e3651bffb5a0d61179c5dca15f03462fc38d894363e9fb60f91b8be13880"
