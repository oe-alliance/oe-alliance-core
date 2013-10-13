SRCDATE = "20131013"

KV = "3.8.7"

SRC_URI[md5sum] = "60c5f48cdad81c32b49276bc6a9d20c7"
SRC_URI[sha256sum] = "42bd4945a7139d11dbcbcdf0e8c8f21d9b03d001bfe7dc23069f921ca93b9f82"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-gb800xxplus-drivers-${KV}-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

