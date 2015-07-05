SRCDATE = "20150608"

KV = "3.14.2"

SRC_URI[md5sum] = "cce8d458c3ebd5ab3e1a0fbcd8e27ea7"
SRC_URI[sha256sum] = "9cc447eecc7fb6d9edab04689249f225e9c0bb688b867ea02e72eb8183d4839d"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7362-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
