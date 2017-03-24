KV = "4.1.21"
SRCDATE = "20170318"
GCC = "6.3.0"

SRC_URI = "http://xpeedlx.info/${MACHINE_DRIVER}-drivers-${KV}-${GCC}-${SRCDATE}.zip"
require ultramini-dvb-modules.inc

SRC_URI[md5sum] = "8b592f008320f9ccc6152ee4444bfe84"
SRC_URI[sha256sum] = "72fe0a8451b6dcc09d2345cd1fb4810e4002dfbb72e496c14f5d7bf336bb178c"
