SRCDATE = "20161224"

KV = "4.0.1"

SRC_URI[md5sum] = "3db12c5ceb38f852d339cd2ef160697a"
SRC_URI[sha256sum] = "cb5df8f143962684ae31e350249738f6b1d52cf82eafef5b7d6b8b85b42e268d"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7356-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
