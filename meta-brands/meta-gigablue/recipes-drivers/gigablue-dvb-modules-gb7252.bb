SRCDATE = "20170122"

KV = "3.14.28-1.12"

SRC_URI[md5sum] = "9c1dcec6392e6525347c3f0bf0840a62"
SRC_URI[sha256sum] = "8bdfa354af5131ed2dff91ee78fb58d2bad8333a5c8308a035cec6dfeab64731"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7252-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
