SRCDATE = "20161219"

KV = "4.0.1"

SRC_URI[md5sum] = "806245aaaad31b3d3f728bdcdb69852e"
SRC_URI[sha256sum] = "2066dd03b07edc72b83fb4c89bbf5879ae14f57b8108ab057f2324f2b2a96c22"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7358-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
