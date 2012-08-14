SRCDATE = "20120814"

KV = "3.1.1"

SRC_URI[md5sum] = "a2d1bf236d6c1b244e78b0aafd61e8c1"
SRC_URI[sha256sum] = "6e395bd870fc9e5c36bfd43789aa6675f8db7ca687a6f05aeed3180fe780b80b"

SRC_URI = "http://archiv.openmips.com/gigablue-gb800xx-drivers-${KV}-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

