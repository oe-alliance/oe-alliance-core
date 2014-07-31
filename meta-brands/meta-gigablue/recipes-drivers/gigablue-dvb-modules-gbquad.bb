SRCDATE = "20140731"

KV = "3.3.8-2.0"

SRC_URI[md5sum] = "2c110839921c45e7dc19dcddbdad7d08"
SRC_URI[sha256sum] = "345aa8324e5ad76391ffcaf2bc0eb6055bf4dedd00e8c56257248256664a85da"

SRC_URI = "http://archiv.openmips.com/gigablue-drivers-${KV}-gbquad-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

