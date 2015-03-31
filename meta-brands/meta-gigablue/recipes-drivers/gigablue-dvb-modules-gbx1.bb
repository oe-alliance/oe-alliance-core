SRCDATE = "20141231"

KV = "3.14.2"

SRC_URI[md5sum] = "c7fac9bf4d7dc508b1280ceea2a6960b"
SRC_URI[sha256sum] = "40588bb60b37a332b2f576ab643829c0a3936865c12134735c9d7c139ba57a0e"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-gbultra-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

