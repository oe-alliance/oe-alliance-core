SRCDATE = "20130913"

KV = "3.8.7"

SRC_URI[md5sum] = "328d594d2400184b58476f8bd4e941e3"
SRC_URI[sha256sum] = "9dd17998c13bd0ab107eff7953ea9cb8df597c77dd0b0f1c88947e1312d1f2ef"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-gb800xxplus-drivers-${KV}-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

