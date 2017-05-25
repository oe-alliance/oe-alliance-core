SRCDATE = "20170525"

KV = "4.8.3"

SRC_URI[md5sum] = "eb8e6d4b3e97d82118ed7c836e6d4d25"
SRC_URI[sha256sum] = "2ffb41e3750f3059534ce4ca4584e190753fff5d8d43344ad3209c0bbda44a81"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7358-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
