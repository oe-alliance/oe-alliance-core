require bcmdriver.inc

SRC_URI[dm520.md5sum] = "7a4f411b7e2ad0fd2f1ac11af4b2707d"
SRC_URI[dm520.sha256sum] = "efa83b6302b6aea417f52996636ce0eb257c7f32f331646d2aedae8b84a4f37b"

inherit opendreambox-precompiled-binary2

COMPATIBLE_MACHINE = "^(dm520)$"
