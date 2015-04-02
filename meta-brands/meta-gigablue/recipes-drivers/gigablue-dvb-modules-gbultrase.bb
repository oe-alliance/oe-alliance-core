SRCDATE = "20140326"

KV = "3.14.2"

SRC_URI[md5sum] = "f705557af5060de28aab062bba553432"
SRC_URI[sha256sum] = "06f49086c239c55254413f6560f9b0aa475079ebf1e4d8f61c90be8a26788d14"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-gbultraseries-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
