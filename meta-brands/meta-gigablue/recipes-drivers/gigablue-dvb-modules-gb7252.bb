SRCDATE = "20170113"

KV = "3.14.28"

SRC_URI[md5sum] = "1e0b651acdfc032be3fa4a900e593f15"
SRC_URI[sha256sum] = "ab6e03d3a55227aa351dd69c16a5295649fc1b783314483cc3de7934d3a47246"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7252-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
