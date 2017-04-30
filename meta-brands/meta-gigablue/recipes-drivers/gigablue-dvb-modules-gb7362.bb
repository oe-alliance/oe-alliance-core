SRCDATE = "20170302"

KV = "4.8.3"

SRC_URI[md5sum] = "76b6a4baef4795ebf3ae58794feca5cc"
SRC_URI[sha256sum] = "a67cd3c47b800300e4816debb76b0b21dcb4515e85bf16c9995d55483c65f1ee"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7362-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
