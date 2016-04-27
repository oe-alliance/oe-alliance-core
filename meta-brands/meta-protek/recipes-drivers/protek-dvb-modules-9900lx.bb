KV = "4.1.21"
GCCREV = "5.3.0"
SRCDATE = "20160427"

SRC_URI = "http://source.mynonpublic.com/protek/${MACHINE}-drivers-${KV}-${GCCREV}-${SRCDATE}.zip"
require protek-dvb-modules.inc

SRC_URI[md5sum] = "b90f6282581fe28b5b4b846f080e8376"
SRC_URI[sha256sum] = "a2c960e22abe004e6e3925dcc8b5a0ec4b56191f695abf8596a7d20e42ab1f54"
