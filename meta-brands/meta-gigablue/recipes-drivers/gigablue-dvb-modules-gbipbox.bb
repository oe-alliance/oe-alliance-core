SRCDATE = "20160225"

KV = "4.0.1"

SRC_URI[md5sum] = "eee7bb6138ef6df788a96a65b09b1e73"
SRC_URI[sha256sum] = "3031b3f5bd1ac6f4295a31853992791ad1e813080060e003bffb4241d6c52576"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7358-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
