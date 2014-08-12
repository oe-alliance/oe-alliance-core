SRCDATE = "20140812"

KV = "3.14.2"

SRC_URI[md5sum] = "f915a2d3cb8b3d18ec4472263a25e67f"
SRC_URI[sha256sum] = "a0af8eba5b9edb3a7a7637751857b0b0726416c55f122f66ee3aa7fd8804e6fc"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-gbquadplus-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

