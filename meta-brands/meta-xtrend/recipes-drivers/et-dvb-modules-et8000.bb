KV = "4.10.6"
SRCDATE = "20180412"

SRC_URI = "http://source.mynonpublic.com/xtrend/${MACHINE}-drivers-${KV}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "5bb8520ca52f013fd368312e30c60a73"
SRC_URI[sha256sum] = "c88e18a7eecb432c5c856f7d178310a3ccbe5f622b6544bf40a707342c8ac7c3"
