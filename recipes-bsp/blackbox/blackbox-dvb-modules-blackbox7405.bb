KV = "3.9.7"
SRCDATE = "20140325"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "f481b29508bd435b0572007adf1b64bc"
SRC_URI[sha256sum] = "1522371c05389d49ce3e2af51f707ad5fb5faa3b8de8817e4c26b5116820fe1d"

SRC_URI = "http://pluginvalley.kr/bcm/driver/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
