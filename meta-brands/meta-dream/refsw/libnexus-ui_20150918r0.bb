require libnexus-ui.inc

RDEPENDS_${PN} = "bcmdriver"

SRC_URI[dm520.md5sum] = "434d0b274834606026115e416501f851"
SRC_URI[dm520.sha256sum] = "4f75dff8d7f5220092741beed561845bddede8c431ed3e844f36cf6a047ee190"

inherit opendreambox-precompiled-binary2

COMPATIBLE_MACHINE = "^(dm520)$"
