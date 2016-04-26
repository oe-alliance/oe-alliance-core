KV = "4.1.21"
GCCREV = "5.3.0"
SRCDATE = "20160426"

SRC_URI = "http://source.mynonpublic.com/protek/${MACHINE}-drivers-${KV}-${GCCREV}-${SRCDATE}.zip"
require protek-dvb-modules.inc

SRC_URI[md5sum] = "ec0bd4c0c017042350d8b380b59603db"
SRC_URI[sha256sum] = "3a3ecb49c436473f77554f32cf744cedb24b278f985fb8806bfa25a738225791"
