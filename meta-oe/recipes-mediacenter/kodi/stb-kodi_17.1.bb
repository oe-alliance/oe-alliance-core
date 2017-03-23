SUMMARY = "Kodi Media Center"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE.GPL;md5=930e2a5f63425d8dd72dbd7391c43c46"

BRANCH = "Krypton"
PV = "17.1"
PR = "r12"

SRC_URI = "https://github.com/xbmc/xbmc/archive/${PV}-Krypton.tar.gz"

SRC_URI[md5sum] = "5e1fe4f85373aaaafba81185401ca14f"
SRC_URI[sha256sum] = "303f3903cbb57ccc2961f09cf3746505542bcb129a464f0687d7ca8601cebbee"

require stb-kodi.inc
