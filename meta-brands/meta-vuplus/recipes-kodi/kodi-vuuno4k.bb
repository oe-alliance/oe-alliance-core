require kodi.inc

COMPATIBLE_MACHINE = "^(vuuno4k)$"

BUILD_PR = "r0"
GLPR = "20161019_r0"

EXTRA_OECONF += " --with-platform=vuplus-arm --with-ffmpeg=force_vuplus_arm "

SRC_URI[xbmc-support.md5sum] = "d6088ee71363bc7e78fef5539840e582"
SRC_URI[xbmc-support.sha256sum] = "29a9cad3135e5fa68d21af819e0cd035eda1ba11b7e065bd67b7b2a2f9b3a5fe"
