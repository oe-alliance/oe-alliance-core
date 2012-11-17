SRCDATE = "20121116"

KV = "2.6.37-2.8"

SRC_URI = "http://archiv.openmips.com/gigablue-quad-drivers-${KV}-${SRCDATE}.zip"
SRC_URI[md5sum] = "c53d25417dac5fb5ddc2dd9d72355fcb"
SRC_URI[sha256sum] = "b4a2e5c899e86a3bbd497eb07ef341bbf228ade1bdea953fb95fd5574dea8dbd"

require gigablue-dvb-modules.inc

