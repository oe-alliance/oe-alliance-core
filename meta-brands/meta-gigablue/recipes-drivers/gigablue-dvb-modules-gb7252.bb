SRCDATE = "20170116"

KV = "3.14.28-1.12"

SRC_URI[md5sum] = "c37044fb3f760024afcb2cb91329d3dc"
SRC_URI[sha256sum] = "2b3e1d9892fe7a30e09715b8f19da4f3e42e9a5339bdb60d58fa734695eb473b"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7252-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
