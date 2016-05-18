KV = "3.14.21"
SRCDATE = "20160511"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "a22aa262340ea06f9194719ee58565e6"
SRC_URI[sha256sum] = "71097c5e016ad7c387102e323463c41c3384449376900391dd92c066388b4e84"

SRC_URI = "http://source.mynonpublic.com/unibox/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
