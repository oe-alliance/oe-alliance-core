SRCDATE = "20180403"

KV = "4.8.3"

SRC_URI[md5sum] = "74b4624bfaa155e47b73b964c936f656"
SRC_URI[sha256sum] = "2b15c05456565a50b01a9ac281f8701d0ed5e4f3e5ffd5110c3d6df75d434fbc"

SRC_URI = "http://source.mynonpublic.com/gigablue/drivers/gigablue-drivers-${KV}-BCM7358-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
