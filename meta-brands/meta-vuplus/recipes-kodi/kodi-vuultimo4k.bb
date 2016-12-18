require kodi.inc

COMPATIBLE_MACHINE = "^(vuultimo4k)$"

BUILD_PR = "r0"
GLPR = "20161206_r0"

EXTRA_OECONF += " --with-platform=vuplus-arm --with-ffmpeg=force_vuplus_arm "

SRC_URI[xbmc-support.md5sum] = "765b8c18ca1e452cbb4b387a01cdefa8"
SRC_URI[xbmc-support.sha256sum] = "e4e469dd6cd21fa9d5fd59a662aa526169ace6ec59854ad0ee416baa66af45ae"
