KV = "4.1.21"
SRCDATE = "20160427"
GCC = "5.3.0"

SRC_URI = "http://xpeedlxclass.eu/${MACHINE_DRIVER}-drivers-${KV}-${GCC}-${SRCDATE}.zip"
require ultramini-dvb-modules.inc

SRC_URI[md5sum] = "b6d039f418080253e3058bcf1691066c"
SRC_URI[sha256sum] = "12a231336f9b9a0d1da8941398d2e3fb70c9bd1f4cd96a6284ef1f3e61cd21d1"
