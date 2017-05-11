require bcmdriver.inc

SRC_URI[dm520.md5sum] = "ffff44dc7687d63a868d84f9be25bdac"
SRC_URI[dm520.sha256sum] = "93aa87e8237f6fa46b9018631cec482208dd0b66188b1e0ce2c6ee8ba392d5dc"

COMPATIBLE_MACHINE = "dm520"

inherit opendreambox-precompiled-binary3
