KV = "4.4.8"
GCC = "6.3.0"
SRCDATE = "20170116"

SRC_URI = "http://www.et-view.com/img_up/shop_pds/bh190/Img_Xtrend/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "2113ea18f0d0344198b42c4aacee7567"
SRC_URI[sha256sum] = "f51cfb5dc42a560f430454bc76cfd31ea38e79323c87df9124587f41eb3e24e3"
