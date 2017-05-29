SRCDATE = "20170525"

KV = "4.8.3"

SRC_URI[md5sum] = "3f39ab759418c8876b743e33d4a4f22a"
SRC_URI[sha256sum] = "4d647363c2530b91de3ac5ed6625e4d7b34487278d036de78c16dc5f8ff498a1"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7356-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
