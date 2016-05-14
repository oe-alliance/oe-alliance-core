require kodi.inc

COMPATIBLE_MACHINE = "^(vusolose)$"

BUILD_PR = "r0"
GLPR = "20160331_r0"

EXTRA_OECONF += " --with-platform=vuplus --with-ffmpeg=force_vuplus_mips "

SRC_URI[xbmc-support.md5sum] = "831014212eed47e36ec084f2e803e2d8"
SRC_URI[xbmc-support.sha256sum] = "97bfc26a316bcba4b897f81f31179e8861cc123a0b4d8589a2290f3cd7268c1d"
