SRCDATE = "20170803"

KV = "3.9.6"

SRC_URI[md5sum] = "efeb18d19102ea11bc6fbca060dde88e"
SRC_URI[sha256sum] = "da17ed78df5a42c9a99cdb4bd70ae61f2cf4b666e53081371ec55c3e21a3c8c1"

SRC_URI = "http://source.mynonpublic.com/gigablue/drivers/gigablue-drivers-${KV}-BCM7325-${SRCDATE}.zip"

require gigablue-dvb-modules.inc