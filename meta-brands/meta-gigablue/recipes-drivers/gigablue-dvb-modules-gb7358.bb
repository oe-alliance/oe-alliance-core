SRCDATE = "20170302"

KV = "4.8.3"

SRC_URI[md5sum] = "60a963f1172cae511957a038fb920da8"
SRC_URI[sha256sum] = "c8908ece093128c64f661477f916188ee3333a45a12808cc428762ddd487219b"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7358-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
