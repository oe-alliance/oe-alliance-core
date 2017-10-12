require libvc5driver.inc

SRC_URI[dm900.md5sum] = "f571dd9298ea59a00aae6f8cac2aa6d8"
SRC_URI[dm900.sha256sum] = "db830ea28f9a9ffab14261e77ba06522b212809ab7b6bd6872180f4eb62f0568"

PRECOMPILED_ARCH = "${MACHINE_ARCH}"
PRECOMPILED_ARCH_dm920 = "dm900"

inherit opendreambox-precompiled-binary3
