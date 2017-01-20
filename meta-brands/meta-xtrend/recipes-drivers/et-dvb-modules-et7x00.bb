KV = "4.4.8"
GCC = "5.3.0"
SRCDATE = "20170111"

SRC_URI = "http://www.et-view.com/img_up/shop_pds/bh190/Img_Xtrend/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "d50918812ad3fa41cc91c610ae121041"
SRC_URI[sha256sum] = "472c655274d5a981b188a234c484e4b89e12e94f30f070f0a8898abedfd00840"
