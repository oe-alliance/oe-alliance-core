SRCDATE = "20140412"

KV = "3.3.8-2.0"

SRC_URI[md5sum] = "edb717344663fbc42cd47d8e988b111d"
SRC_URI[sha256sum] = "41a3a5e2ed8f54225fa00d83e8c7a6d786e8984fed431a9b99a8ffcb2dd121fe"

SRC_URI = "http://archiv.openmips.com/gigablue-drivers-${KV}-gbquad-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

