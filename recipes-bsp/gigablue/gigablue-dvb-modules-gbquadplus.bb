SRCDATE = "20140505"

KV = "3.3.8-2.0"

SRC_URI[md5sum] = "dca2a7fbc550565a0f386ef8a62329ba"
SRC_URI[sha256sum] = "85847ef0efbe846df6c439e0b1b68447da38e00086beab16e2bc77cafeb933a9"

SRC_URI = "http://archiv.openmips.com/gigablue-drivers-${KV}-gbquadplus-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

