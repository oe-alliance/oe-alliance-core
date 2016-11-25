require kodi.inc

COMPATIBLE_MACHINE = "^(vuultimo4k)$"

BUILD_PR = "r0"
GLPR = "20161020_r0"

EXTRA_OECONF += " --with-platform=vuplus-arm --with-ffmpeg=force_vuplus_arm "

SRC_URI[xbmc-support.md5sum] = "2276414b14f9818935f0b6967d25b617"
SRC_URI[xbmc-support.sha256sum] = "24f1c5500ec0371a177189cfc19034295a3bfa554bdfba4f648a06ded64e48dd"
