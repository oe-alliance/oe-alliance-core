SRCDATE = "20140523"

KV = "3.3.8-2.0"

SRC_URI[md5sum] = "c9d25bf18e176640534bbd9ec4fb6cf5"
SRC_URI[sha256sum] = "ccb341ac765e81d9f7534ff7bd8ca1b4d6c6ebb86186e736b6438840524b9f1e"

SRC_URI = "http://archiv.openmips.com/gigablue-drivers-${KV}-gbquadplus-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

