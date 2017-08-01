SRCDATE = "20170726"

KV = "4.8.3"

SRC_URI[md5sum] = "f3609a0d0d158f7e07500415b3e2f521"
SRC_URI[sha256sum] = "2419457f531f3abae440346c8cf8d7688161acf668a489a504806091933f4304"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7358-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
