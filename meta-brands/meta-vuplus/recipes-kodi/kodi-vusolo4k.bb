require kodi.inc

COMPATIBLE_MACHINE = "^(vusolo4k)$"

BUILD_PR = "r0"
GLARCH = "-arm"
GLPR = "20150603_p0"

EXTRA_OECONF += " --with-platform=dvbboxarm "

SRC_URI[xbmc-support.md5sum] = "3803968ff78af7437982caf02647238b"
SRC_URI[xbmc-support.sha256sum] = "d8e556854a29ef3dd78fab18454f0e91f01a23b7df299400276deb1f8fb495c6"
