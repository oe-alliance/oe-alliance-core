KV = "4.1.21"
GCCREV = "6.3.0"
SRCDATE = "20170310"

SRC_URI = "http://source.mynonpublic.com/protek/${MACHINE}-drivers-${KV}-${GCCREV}-${SRCDATE}.zip"
require protek-dvb-modules.inc

SRC_URI[md5sum] = "110bf3bd8e4554ea4a3745168dbca9bc"
SRC_URI[sha256sum] = "1edc5e6e2dfc9886aab8ff366afc7acee0135c5b73aa0d806af644e98bf69143"
