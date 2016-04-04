require kodi.inc

COMPATIBLE_MACHINE = "^(vuduo2)$"

BUILD_PR = "r0"
GLPR = "20160331_r0"

EXTRA_OECONF += " --with-platform=vuplus --with-ffmpeg=force_vuplus_mips "

SRC_URI[xbmc-support.md5sum] = "f3db678550f3654fcc8dfbb875678943"
SRC_URI[xbmc-support.sha256sum] = "758e75966c1ca513bbeb7eaef0d0359207232ba0e7f4f5e2574c146f5e09cab3"

