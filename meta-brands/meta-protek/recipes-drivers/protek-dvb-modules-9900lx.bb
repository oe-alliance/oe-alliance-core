KV = "4.1.21"
GCCREV = "5.3.0"
SRCDATE = "20170310"

SRC_URI = "http://source.mynonpublic.com/protek/${MACHINE}-drivers-${KV}-${GCCREV}-${SRCDATE}.zip"
require protek-dvb-modules.inc

SRC_URI[md5sum] = "a1a3c1ee1e5aecd405d5afe9e0629de1"
SRC_URI[sha256sum] = "664d76c883a3009c3dba35feb17b659b9e4b43b7e5b393c608c5bdb5d39a6845"
