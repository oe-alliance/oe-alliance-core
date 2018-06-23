SRCDATE = "20180403"

KV = "4.8.3"

SRC_URI[md5sum] = "46cf8256dc42dd4817d6d7eb5d6cc284"
SRC_URI[sha256sum] = "74161431f193bd77dd17951ba1a0e2e19670cb74037c503f998055e5b7031265"

SRC_URI = "http://source.mynonpublic.com/gigablue/drivers/gigablue-drivers-${KV}-BCM7362-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
