SRCDATE = "20180403"

KV = "4.8.3"

SRC_URI[md5sum] = "cb92f5bc77d9f1fa527ac2dc4f01995c"
SRC_URI[sha256sum] = "a28d4d57233f9ad18b78e5ce79162c668bf9727687621329b34310bca5acafe7"

SRC_URI = "http://source.mynonpublic.com/gigablue/drivers/gigablue-drivers-${KV}-BCM7356-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
