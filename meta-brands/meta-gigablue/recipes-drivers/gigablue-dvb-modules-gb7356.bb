SRCDATE = "20151023"

KV = "3.14.2"

SRC_URI[md5sum] = "9f590ebf41c0922485ac34fe59c93f08"
SRC_URI[sha256sum] = "2c460714734cbf8bf257c9577f5702e330b3f89a7bd3ad3b12c2555c09e97138"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7356-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
