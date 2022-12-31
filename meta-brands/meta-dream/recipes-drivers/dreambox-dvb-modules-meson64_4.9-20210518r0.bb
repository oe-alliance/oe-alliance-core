SRC_URI[dreamone.md5sum] = "3651abcece85309921017f7aad331be3"
SRC_URI[dreamone.sha256sum] = "c2b84a3e2750719b78c4d2b447e1b2300f60a3eb39b5b1ee0b45d7adf472562b"
SRC_URI[dreamtwo.md5sum] = "a190b1d07b08c25bf6660a7b7057d9f2"
SRC_URI[dreamtwo.sha256sum] = "1fe751b39429a51f31657b4c3b98f2acb13c1299f999a0593641b71a4c7e315d"

PRECOMPILED_NAME = "dreambox-dvb-modules"

require dreambox-dvb-modules-meson64.inc

RREPLACES:${PN} += "dreamframeinject"
RPROVIDES:${PN} += "dreamframeinject"
RCONFLICTS:${PN} += "dreamframeinject"

COMPATIBLE_MACHINE = "^(dreamone|dreamtwo)$"
