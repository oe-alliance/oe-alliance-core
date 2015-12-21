SRCDATE = "20151218"

KV = "4.0.1"

SRC_URI[md5sum] = "b7d8fbd102814af50688acfed1408bc7"
SRC_URI[sha256sum] = "750a7c77fd2f48052f8b642ead3a9672f15ae4c957e3aed2a46f5d8025a81e16"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7358-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
