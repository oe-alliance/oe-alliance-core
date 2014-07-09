KV = "3.14.2"
SRCDATE = "20140709"

SRC_URI[md5sum] = "fab301c2847ba25cddb7af9accfd574f"
SRC_URI[sha256sum] = "b9a652c37a0302cffef93d533af892f96c428438b65ca79a1e4387b45fb69214"

SRC_URI = "http://code-ini.com/software/drivers/ini-hdp-drivers-${KV}-${SRCDATE}.zip"

require ini-dvb-modules.inc
