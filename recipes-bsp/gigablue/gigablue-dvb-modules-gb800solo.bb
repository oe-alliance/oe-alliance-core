SRCDATE = "20130311"

KV = "3.1.1"

SRC_URI[md5sum] = "0c0856abb325f3a210def0af85b9675d"
SRC_URI[sha256sum] = "cbf3db19661749060aa05d0dbf6ad455d48da97eb729d786fdcc48656eaba558"

SRC_URI = "http://archiv.openmips.com/gigablue-gb800xx-drivers-${KV}-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

