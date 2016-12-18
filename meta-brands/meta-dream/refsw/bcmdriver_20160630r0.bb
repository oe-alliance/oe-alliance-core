require bcmdriver.inc

SRC_URI[dm900.md5sum] = "c98c8b71cc15071717975e9d8a1dd98c"
SRC_URI[dm900.sha256sum] = "af2d8e19f45adb42d6315caf13313bb66ab536f12b0049056fc7e10be646070a"

inherit opendreambox-precompiled-binary3

COMPATIBLE_MACHINE = "^(dm900)$"
