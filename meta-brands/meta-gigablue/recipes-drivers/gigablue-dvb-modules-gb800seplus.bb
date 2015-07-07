SRCDATE = "20150703"

KV = "3.14.2"

SRC_URI[md5sum] = "7e9084d4a7361d789206d49325a91f5c"
SRC_URI[sha256sum] = "d3fc7280d38e03d74203e0ea48994f223b36c7f0d3e9bd0f6348c147c89f4291"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7358-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
