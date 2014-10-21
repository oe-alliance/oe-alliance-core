SRCDATE = "20141020"

KV = "3.14.2"

SRC_URI[md5sum] = "800c803f38fb6405a80ccceafbf45921"
SRC_URI[sha256sum] = "0ad533e5a80ab24c55fa94b752bfc14ef97d6e62eeb22d3e0d0f7be68e3b9f19"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-gb800xxplus-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
