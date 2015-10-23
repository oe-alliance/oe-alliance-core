SRCDATE = "20151023"

KV = "3.14.2"

SRC_URI[md5sum] = "da1e892b0657117cb815b277176ff9c3"
SRC_URI[sha256sum] = "513e9241a51eedfcc51b395c94056968e4378624523a5baaf2457d2eaa7f4ab9"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7358-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
