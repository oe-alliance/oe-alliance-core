KV = "4.1.21"
GCCREV = "6.3.0"
SRCDATE = "20170424"

SRC_URI = "http://source.mynonpublic.com/protek/${MACHINE}-drivers-${KV}-${GCCREV}-${SRCDATE}.zip"
require protek-dvb-modules.inc

SRC_URI[md5sum] = "a3d9e559d735eed2bc7a443c4bf0f524"
SRC_URI[sha256sum] = "ba6c20ea19420915d07faf95f442b4af9e1fc4d7f99c48747f52b210603c1189"
