SRCDATE = "20141111"

KV = "3.14.2"

SRC_URI[md5sum] = "35252a29b8e15172498e0a123a14de1d"
SRC_URI[sha256sum] = "96441149ea0160be24bcccea2be757b4e4e15a85abe0853812705bd2369a8350"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-gbultra-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

