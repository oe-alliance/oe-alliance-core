require bcmdriver.inc

SRC_URI[dm900.md5sum] = "fc652d3ded11063767c7e95213b7ffab"
SRC_URI[dm900.sha256sum] = "5a22bbdfa3cdb8bae6dd30e53087ddc0be1dda672e601c9989afb3d661d871ea"

inherit opendreambox-precompiled-binary3

COMPATIBLE_MACHINE = "^(dm900)$"
