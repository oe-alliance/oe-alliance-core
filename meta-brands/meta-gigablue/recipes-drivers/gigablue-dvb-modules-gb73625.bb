SRCDATE = "20180403"

KV = "4.8.3"

SRC_URI[md5sum] = "33305fd3bccdc66137252d3765a9bd39"
SRC_URI[sha256sum] = "07b14ad80abed5c601dc76f036746769576461e628ba0c0184d7e38fc409a978"

SRC_URI = "http://source.mynonpublic.com/gigablue/drivers/gigablue-drivers-${KV}-BCM73625-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
