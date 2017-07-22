require libnexus-ui.inc

RDEPENDS_${PN} = "bcmdriver"

SRC_URI[dm520.md5sum] = "66e1ed7c1350ddef375545f670239424"
SRC_URI[dm520.sha256sum] = "3f7e55c2fe4033d49cb181981a8b29c4d29e132efd0b2197d8e552b0fc62cd01"

inherit opendreambox-precompiled-binary3
