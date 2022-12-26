DESCRIPTION = "WavPack is a completely open audio compression format providing lossless, high-quality lossy, and a unique hybrid compression mode."
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=bb5d037e3ad41a3c84c9f2d8bb65a7b4"

SRC_URI = "http://wavpack.com/wavpack-${PV}.tar.bz2"
SRC_URI[md5sum] = "0b3e0142b3b68c742865c1d378eda607"
SRC_URI[sha256sum] = "8cbfa15927d29bcf953db35c0cfca7424344ff43ebe4083daf161577fb839cc1"

inherit autotools lib_package
