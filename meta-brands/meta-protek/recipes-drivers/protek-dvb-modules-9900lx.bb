KV = "4.1.21"
GCCREV = "6.3.0"
SRCDATE = "20170318"

SRC_URI = "http://source.mynonpublic.com/protek/${MACHINE}-drivers-${KV}-${GCCREV}-${SRCDATE}.zip"
require protek-dvb-modules.inc

SRC_URI[md5sum] = "1a2be5d3703d5a28ee81fe271a1412b0"
SRC_URI[sha256sum] = "03b5e1fd3a9f8a61c861ad0baa958f7e49b793abca5ab78fa794a833c7bf7144"
