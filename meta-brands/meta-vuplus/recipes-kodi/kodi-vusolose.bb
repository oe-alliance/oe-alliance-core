require kodi.inc

COMPATIBLE_MACHINE = "^(vusolose)$"

BUILD_PR = "r0"
GLARCH = ""
GLPR = "20150424_p0"

EXTRA_OECONF += " --with-platform=dvbbox "

SRC_URI[xbmc-support.md5sum] = "28d0a2461f5a8244cbc908dd0e0ab5bc"
SRC_URI[xbmc-support.sha256sum] = "ecd7520c8649fc426c8bc941a30daec000cd10fc21fcdd9a494bf23f497d7423"

#GLPR = "20141202_p0"
#SRC_URI[xbmc-support.md5sum] = "9d0c73505484823e3816b5577f28a8a8"
#SRC_URI[xbmc-support.sha256sum] = "5483e24db81efca03120dbf0ef4cc423d2665ebc4d52149be15d75a0ae9b626d"
