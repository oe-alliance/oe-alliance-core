SRCDATE = "20160526"

KV = "4.0.1"

SRC_URI[md5sum] = "69bcfe01c9293d9dc9d257e59fc61ffc"
SRC_URI[sha256sum] = "7bb4ea71f8812f29c84b4c460e4742e4b9f280bc5e2fdc58dbbcc3e7dbd2aa1d"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM73625-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
