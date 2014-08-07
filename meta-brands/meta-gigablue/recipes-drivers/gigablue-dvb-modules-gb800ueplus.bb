SRCDATE = "20140731"

KV = "3.14.2"

SRC_URI[md5sum] = "aafd0fd558704b32c0f72ec45e44999c"
SRC_URI[sha256sum] = "e7d1e714c36e2e6c884eb280879a63a6612b8e84bd90d76dcd3f4de18b5fab6d"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-gb800xxplus-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

