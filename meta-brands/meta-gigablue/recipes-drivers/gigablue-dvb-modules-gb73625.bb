SRCDATE = "20181130"

KV = "4.8.3"

SRC_URI[md5sum] = "5c7a080c7fa361f09720c7439798f682"
SRC_URI[sha256sum] = "5de17f8de14e5a55e5489a4e63ca187315b0e98918a11f543651aa82827ad57f"

SRC_URI = "http://source.mynonpublic.com/gigablue/drivers/gigablue-drivers-${KV}-BCM73625-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
