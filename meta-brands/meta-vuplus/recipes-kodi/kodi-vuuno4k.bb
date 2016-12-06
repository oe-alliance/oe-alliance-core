require kodi.inc

COMPATIBLE_MACHINE = "^(vuuno4k)$"

BUILD_PR = "r0"
GLPR = "20161206_r1"

EXTRA_OECONF += " --with-platform=vuplus-arm --with-ffmpeg=force_vuplus_arm "

SRC_URI[xbmc-support.md5sum] = "b0f62cb425b0f5bc5dc6ebcbe76f0890"
SRC_URI[xbmc-support.sha256sum] = "b2b91ec85cc7edfef86218906a8743868e372fb5f2736fa1ce066335a23c6bb1"
