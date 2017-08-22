SRCDATE = "20170726"

KV = "4.8.3"

SRC_URI[md5sum] = "f69aa4e629967ce5fb566c1b8780610a"
SRC_URI[sha256sum] = "f50764967dfa9c24512dcb7d90b16a216dafc3a5c00cc6108ea651c4adc3fbbe"

SRC_URI = "http://impex-sat.de/gigablue/drivers/gigablue-drivers-${KV}-BCM7356-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
