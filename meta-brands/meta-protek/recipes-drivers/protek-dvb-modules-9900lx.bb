KV = "4.1.21"
GCCREV = "5.3.0"
SRCDATE = "20160610"

SRC_URI = "http://source.mynonpublic.com/protek/${MACHINE}-drivers-${KV}-${GCCREV}-${SRCDATE}.zip"
require protek-dvb-modules.inc

SRC_URI[md5sum] = "e9d624b727e2f9d1880802c4d9007a08"
SRC_URI[sha256sum] = "95227c45568b5b532a9c5458071ebf2e7cb29a1ac18ef87f930b37094a0ec1a3"
