require kodi.inc

COMPATIBLE_MACHINE = "^(vusolo2)$"

BUILD_PR = "r0"
GLPR = "20160331_r0"

EXTRA_OECONF += " --with-platform=vuplus --with-ffmpeg=force_vuplus_mips "

SRC_URI[xbmc-support.md5sum] = "e29a91b185133ec60a59e94a8229d2b4"
SRC_URI[xbmc-support.sha256sum] = "3c56b7ee890b3e21f378acd79db3752d721de0880b6d763bbd37fa942c2ae2b5"
