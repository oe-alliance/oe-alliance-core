SUMMARY = "MJPEG video capture/editting/playback MPEG encoding"
HOMEPAGE = "http://sourceforge.net/projects/mjpeg/"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"
DEPENDS = "jpeg libpng"
PR = "r2"

SRC_URI = "${SOURCEFORGE_MIRROR}/mjpeg/${BP}.tar.gz \
           file://no-includedir.patch"
SRC_URI[md5sum] = "903e1e3b967eebcc5fe5626d7517dc46"
SRC_URI[sha256sum] = "bf3541593e71602f7b440c2e7d81b433f53d0511e74642f35bea9b3feded7a97"

inherit autotools

EXTRA_OECONF = " \
        --without-libquicktime \
        --without-libdv \
        --without-dga \
        --without-gtk \
        --without-libsdl \
        --without-v4l \
        --without-x \
"
