SRCDATE = "20141017"

KV = "3.9.6"

SRC_URI[md5sum] = "f23039aa0b6fc4401cfd312fc3ac4a09"
SRC_URI[sha256sum] = "25774561b0b84a8c8ead73b5a26aace5d924c210882f277a1eee5b53b9220b09"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-gb800xx-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
