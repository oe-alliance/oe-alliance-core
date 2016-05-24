KV = "4.1.21"
SRCDATE = "20160524"
GCC = "5.3.0"

SRC_URI = "http://xpeedlxclass.eu/${MACHINE_DRIVER}-drivers-${KV}-${GCC}-${SRCDATE}.zip"
require ultramini-dvb-modules.inc

SRC_URI[md5sum] = "78e4e28ace9690cb5291ce314b5cf743"
SRC_URI[sha256sum] = "302418f20549273abe62cd1c1d1e2d2bf9a098e48152bbab4e21f095c36ae978"
