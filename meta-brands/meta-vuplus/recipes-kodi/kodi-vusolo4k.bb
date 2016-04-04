require kodi.inc

COMPATIBLE_MACHINE = "^(vusolo4k)$"

BUILD_PR = "r0"
GLPR = "20160331_r0"

EXTRA_OECONF += " --with-platform=vuplus-arm --with-ffmpeg=force_vuplus_arm "

SRC_URI[xbmc-support.md5sum] = "1ae0c982f8db9625a7f831874f6f0605"
SRC_URI[xbmc-support.sha256sum] = "1be88ba6d4cbc0a0b1f10c83995132aa9bd5a088cf5b7080e1b0ef90170d96f7"
