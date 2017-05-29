SRCDATE = "20170525"

KV = "4.8.3"

SRC_URI[md5sum] = "5ac15dd2328dcf8f288fb2738eb8114f"
SRC_URI[sha256sum] = "c82a95c4428ebf0b17d3e66f61f4094a7f7394e3986ebc03aecd03ddc91fdf55"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM73625-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
