SRCDATE = "20161219"

KV = "4.0.1"

SRC_URI[md5sum] = "943e41abb5cd56dcf1118e48a9c9b956"
SRC_URI[sha256sum] = "31fc1a20f2a3a0eeee55722e0e16a37ef6165b45a8b8e8294ca8eb81f5cfb298"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM73625-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
