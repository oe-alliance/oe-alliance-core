SRCDATE = "20150407"

KV = "3.14.2"

SRC_URI[md5sum] = "e23009fae735f1dcc894fcf12cc0304f"
SRC_URI[sha256sum] = "60a2bee8d70d4cacb633e604d513b960638d5d291d0a57c227c6ebcda194cb41"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7362-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
