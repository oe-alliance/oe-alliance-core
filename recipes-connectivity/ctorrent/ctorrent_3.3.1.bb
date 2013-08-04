require ctorrent.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

DESCRIPTION += "This is the Enhanced version from the dtorrent project"

SRC_URI = "${SOURCEFORGE_MIRROR}/dtorrent/ctorrent-dnh${PV}.tar.gz \
           file://CVE-2009-1759.patch;striplevel=0 "

S = "${WORKDIR}/${PN}-dnh${PV}"
PR="r1"

SRC_URI[md5sum] = "9bfe42c2dd6a6aabd545fe332bc23e87"
SRC_URI[sha256sum] = "e65d9c7a4da499314126d979a0a79ce5123e0f969c786bfddcee261f14cb648b"
