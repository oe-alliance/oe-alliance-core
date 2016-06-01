KV = "4.1.21"
GCCREV = "5.3.0"
SRCDATE = "20160601"

SRC_URI = "http://source.mynonpublic.com/protek/${MACHINE}-drivers-${KV}-${GCCREV}-${SRCDATE}.zip"
require protek-dvb-modules.inc

SRC_URI[md5sum] = "3a4054f8242405c6c4be2b2359146472"
SRC_URI[sha256sum] = "6888e47cc6115c9f8e2536c282df6b44c6087d4482aee249f07338ade03123e5"
