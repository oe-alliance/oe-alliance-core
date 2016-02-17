SRCDATE = "20151028"

KV = "3.9.6"

SRC_URI[md5sum] = "0ee0cb3a446ad80cc3008bfc489cd9ca"
SRC_URI[sha256sum] = "ca83e04968b7b80c5c62283875c8431da542628052ae3e595a9538f44394dec0"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7325-${SRCDATE}.zip"

require gigablue-dvb-modules.inc