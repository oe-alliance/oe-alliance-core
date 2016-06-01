KV = "4.1.21"
SRCDATE = "20160601"
GCC = "5.3.0"

SRC_URI = "http://xpeedlxclass.eu/${MACHINE_DRIVER}-drivers-${KV}-${GCC}-${SRCDATE}.zip"
require ultramini-dvb-modules.inc

SRC_URI[md5sum] = "239bfd7c8a604493e7e2b8e167b89042"
SRC_URI[sha256sum] = "926bc9a3ec69200d9bb2c03842caafca424628ca71d5a22295d5ad41b3fe113a"
