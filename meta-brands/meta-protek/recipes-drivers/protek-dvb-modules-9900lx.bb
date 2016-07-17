KV = "4.1.21"
GCCREV = "5.3.0"
SRCDATE = "20160715"

SRC_URI = "http://source.mynonpublic.com/protek/${MACHINE}-drivers-${KV}-${GCCREV}-${SRCDATE}.zip"
require protek-dvb-modules.inc

SRC_URI[md5sum] = "c370871325c5e74fab353af8405ff298"
SRC_URI[sha256sum] = "a9c044c2806bd52b97f1b648b0e5318a86f191a38acfd9a523d21c3faec27c4b"
