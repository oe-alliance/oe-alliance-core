SRCDATE = "20180329a"

KV = "4.8.3"

SRC_URI[md5sum] = "732283fc0411607039af1362e43f57c4"
SRC_URI[sha256sum] = "6387be2d72f6434fd99a30e9a9dc179c465e93816b657d6df8c930d02633bdae"

SRC_URI = "http://opensat.de/gigablue/drivers/gigablue-drivers-${KV}-BCM7362-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
