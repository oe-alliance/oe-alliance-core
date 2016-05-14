KV = "4.1.21"
SRCDATE = "20160428"
GCC = "5.3.0"

SRC_URI = "http://gi-et-7000-mini.eu/${MACHINE_DRIVER}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require ultramini-dvb-modules.inc

SRC_URI[md5sum] = "460dcb97232eef0120821b9d173eae70"
SRC_URI[sha256sum] = "ca3fd7906df6d8bd2b2ca545f085e8718d06cf11d1eb59a5827c603dabf10159"
