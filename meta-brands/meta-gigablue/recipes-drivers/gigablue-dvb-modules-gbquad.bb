SRCDATE = "20141020"

KV = "3.14.2"

SRC_URI[md5sum] = "f7a91069ce191f7892dced0338b9811f"
SRC_URI[sha256sum] = "e052e37825409885a78032051cb9020502e68c6be9f22119031859960a4d1ed6"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-gbquadseries-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
