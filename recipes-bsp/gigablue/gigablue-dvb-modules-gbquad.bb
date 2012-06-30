SRCDATE = "20120611"

KV = "2.6.37"

SRC_URI = "http://archiv.openmips.com/gigablue-quad-drivers-${KV}-${SRCDATE}.zip"

SRC_URI[md5sum] = "4ffa71bd1336034d62d276b972bbb078"
SRC_URI[sha256sum] = "b4fce0e78b02098e0110878a300d013fad973015b36347fcf010f61912ab721c"

require gigablue-dvb-modules.inc

