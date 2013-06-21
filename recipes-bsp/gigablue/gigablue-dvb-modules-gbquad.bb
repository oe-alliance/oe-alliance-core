SRCDATE = "20130603"

KV = "3.3.6-1.2"

SRC_URI = "http://archiv.openmips.com/gigablue-quad-drivers-${KV}-${SRCDATE}.zip"
SRC_URI[md5sum] = "7b3fdb02a3fc2150a7800994d8e7374c"
SRC_URI[sha256sum] = "048dce3dcb8deb444c81bdc76846dc9336ce6afbd705fda1587aab45494b6f2e"

require gigablue-dvb-modules.inc

