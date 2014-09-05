SRCDATE = "20140903"

KV = "3.14.2"

SRC_URI[md5sum] = "fd59eab831568e9c4eb69672d913c135"
SRC_URI[sha256sum] = "8e0f9a56bc236c902c5814b292a45512c46012c4eb5f143f925e3d17c41ecd7a"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-gbquadseries-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

