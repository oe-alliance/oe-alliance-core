KV = "3.6.0"
SRCDATE = "20140217"

SRC_URI[md5sum] = "2025d83540f67d4a7c4d67ccaba1afd4"
SRC_URI[sha256sum] = "6718bc4e645c84f9b285745225853a4bc0ad03c3483971c80a5ffb7b6ad3a607"

SRC_URI = "http://code-ini.com/software/drivers/ini-1000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES = "venton-dvb-modules-ventonhde"
RCONFLICTS = "venton-dvb-modules-ventonhde"

require ini-dvb-modules.inc

