SRCDATE = "20170114"

KV = "3.14.28-1.12"

SRC_URI[md5sum] = "f1e5fca2ef541460400131e589169c52"
SRC_URI[sha256sum] = "fb714b4e9801f53a3fd5f923fb3a9a2d3b1f8e7e67687a2ccb6300baaa48ffb1"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7252-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
