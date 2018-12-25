KV = "4.4.8"
GCC = "6.3.0"
SRCDATE = "20170424"

SRC_URI = "http://source.mynonpublic.com/xtrend/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "a5e0bdc479bf8a1403e3248edc3176ac"
SRC_URI[sha256sum] = "b8b7796f8bc2931e4d1b75fe244d13c9e6185ce96a57cadfee3ed0b4903e788e"
