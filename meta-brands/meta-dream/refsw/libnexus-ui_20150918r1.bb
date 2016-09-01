require libnexus-ui.inc

RDEPENDS_${PN} = "bcmdriver"

SRC_URI[dm520.md5sum] = "9cb0c7d4a1b592ae41a0a5567c164997"
SRC_URI[dm520.sha256sum] = "958e48a1b6a805833e71f3ef2688131cc90f86e9a79ce85222dca0a4bad66b50"

inherit opendreambox-precompiled-binary2

COMPATIBLE_MACHINE = "^(dm520)$"
