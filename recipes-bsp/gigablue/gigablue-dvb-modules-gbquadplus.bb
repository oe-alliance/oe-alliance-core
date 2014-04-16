SRCDATE = "20140416"

KV = "3.3.8-2.0"

SRC_URI[md5sum] = "599c3676fa9cd2525ba30268fc9fe870"
SRC_URI[sha256sum] = "1ea918a409b3f7b54c929bde81950de8b3354ff9e2ae38a33e9caa132d0f0be7"

SRC_URI = "http://archiv.openmips.com/gigablue-drivers-${KV}-gbquadplus-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

