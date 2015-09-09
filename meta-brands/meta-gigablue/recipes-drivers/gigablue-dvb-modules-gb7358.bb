SRCDATE = "20150826"

KV = "3.14.2"

SRC_URI[md5sum] = "5b3bbaccd4970d1d578de8734f7bc547"
SRC_URI[sha256sum] = "ed1740e917657a1b0ec607a4e6330313e576a805131e00b8cd208969ef07c695"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7358-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
