KV = "3.18.24"
SRCDATE = "20151118"

SRC_URI = "http://source.mynonpublic.com/protek/${MACHINE_DRIVER}-drivers-${KV}-${SRCDATE}.zip"
require protek-dvb-modules.inc

SRC_URI[md5sum] = "6d25801bb79a66929e7dbcd3835afd20"
SRC_URI[sha256sum] = "ec6a2f8440b23174fb16acb8cd85b2e5c4cda07f92b6b49b57c33955d761c4c0"
