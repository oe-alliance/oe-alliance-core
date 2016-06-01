KV = "4.4.8"
GCC = "5.3.0"
SRCDATE = "20160601"

SRC_URI = "http://www.xtrendet.net/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "cb9697df53f80838e4873c7fc25a1148"
SRC_URI[sha256sum] = "fcd08bb62dfd83b24165dd9c7de87978c5df714392845580a59eb668fd564bcc"
