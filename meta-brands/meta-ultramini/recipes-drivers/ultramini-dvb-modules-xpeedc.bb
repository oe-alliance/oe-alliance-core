KV = "3.18.18"
SRCDATE = "20151021"

SRC_URI = "http://xpeedlxclass.eu/${MACHINE_DRIVER}-drivers-${KV}-${SRCDATE}.zip"
require ultramini-dvb-modules.inc

SRC_URI[md5sum] = "f25aa72e8dd86a24529a76f6b6ca7c43"
+SRC_URI[sha256sum] = "b5a4bbbdc3a828a669ff643ca4618501a468cb9828062cfc001009cd558f501e"
