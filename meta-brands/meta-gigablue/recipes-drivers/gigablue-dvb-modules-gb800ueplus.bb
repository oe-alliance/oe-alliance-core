SRCDATE = "20140505"

KV = "3.8.7"

SRC_URI[md5sum] = "40d880388fbb74016f91b2495393617f"
SRC_URI[sha256sum] = "b7b5a272b60d109151c7e6258510c2c94de8e38d4e5ceb5c864ba3703acda708"

SRC_URI = "http://archiv.openmips.com/gigablue-drivers-${KV}-gb800xxplus-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

