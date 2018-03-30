SRCDATE = "20180330"

KV = "4.8.3"

SRC_URI[md5sum] = "f300c5b6de98bde84044ca09905b43cc"
SRC_URI[sha256sum] = "3f02f21bbcff5550accefb64d9008da310ca3393bc99d08b058a36e1370d523b"

SRC_URI = "http://opensat.de/gigablue/drivers/gigablue-drivers-${KV}-BCM7358-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
