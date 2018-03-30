SRCDATE = "20180329a"

KV = "4.8.3"

SRC_URI[md5sum] = "4e255cbafed31e263695a9b90dec1f94"
SRC_URI[sha256sum] = "0ebf3b8b275e47ef519cfb616c51c8aa4d38aebc806b0d5eff1c6598a2dcf784"

SRC_URI = "http://opensat.de/gigablue/drivers/gigablue-drivers-${KV}-BCM7356-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
