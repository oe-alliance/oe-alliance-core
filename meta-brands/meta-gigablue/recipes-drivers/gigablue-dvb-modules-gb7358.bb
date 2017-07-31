SRCDATE = "20170726"

KV = "4.8.3"

SRC_URI[md5sum] = "54c02e75bb52bb0733129a6ea0359e4e"
SRC_URI[sha256sum] = "cfac0a1dcd0f073e73d7b79513d23670b1e44145beb0e564333da5d60138668f"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7358-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
