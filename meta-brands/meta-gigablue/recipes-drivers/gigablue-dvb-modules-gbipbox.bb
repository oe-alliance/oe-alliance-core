SRCDATE = "20140514"

KV = "3.12.1"

SRC_URI[md5sum] = "40d34082bad646c6fa55bc22e062bf29"
SRC_URI[sha256sum] = "258081c19c53c1da3eadd72b7f707496adb19d4cad0e1e6395b315536c14daae"

SRC_URI = "http://archiv.openmips.com/gigablue-drivers-${KV}-gbipbox-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

