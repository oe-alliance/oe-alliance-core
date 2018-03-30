SRCDATE = "20180329a"

KV = "4.8.3"

SRC_URI[md5sum] = "1e69d2a2f7ee1d1041797d22d5478fef"
SRC_URI[sha256sum] = "f209a943d0a551c6fd5f48b207e59eccb6f50bd20ee6270790795919369e00ff"

SRC_URI = "http://opensat.de/gigablue/drivers/gigablue-drivers-${KV}-BCM73625-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
