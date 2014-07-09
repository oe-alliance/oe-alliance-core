SRCDATE = "20140708"

KV = "3.3.8-2.0"

SRC_URI[md5sum] = "c219705d483580dc7549ce8c1bcda6e5"
SRC_URI[sha256sum] = "af0af0a8bf65b84ca0404c9b09a14ee51c1d086721acdb63765f9e2e09b87138"

SRC_URI = "http://archiv.openmips.com/gigablue-drivers-${KV}-gbquadplus-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

