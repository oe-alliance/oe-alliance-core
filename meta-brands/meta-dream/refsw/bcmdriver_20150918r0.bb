require bcmdriver.inc

SRC_URI[dm520.md5sum] = "e527cabe91027026a6aac506bd209e78"
SRC_URI[dm520.sha256sum] = "a1c3e2d7cf630a23c21a8d8047d8ca1c36289f7fa1fd7a5e5b95cb7566d66fc7"

inherit opendreambox-precompiled-binary2

COMPATIBLE_MACHINE = "^(dm520)$"
