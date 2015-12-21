SRCDATE = "20151218"

KV = "4.0.1"

SRC_URI[md5sum] = "e5e14166c0ebe5255213e1f0548455bb"
SRC_URI[sha256sum] = "7c3c482d20e64f6144d6d4d748eb1bd66a8e4350d1c9c9e83359fc9e67be23d1"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7362-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
