SRCDATE = "20161031"

KV = "4.0.1"

SRC_URI[md5sum] = "6f03576671879abedc2bce638c5ef1d2"
SRC_URI[sha256sum] = "ac92c16ed905712e9726592d501229ee4173875cc48cd8a4bf0607392170a63b"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM73625-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
