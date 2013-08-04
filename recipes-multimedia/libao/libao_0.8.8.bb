DESCRIPTION = "A cross platform audio library"
LICENSE = "GPL"
HOMEPAGE = "http://www.xiph.org/ao/"
SECTION = "libs/multimedia"
DEPENDS = "alsa-lib"
PROVIDES = "libao-alsa libao-alsa-plugin"
PR = "r1"

LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

SRC_URI = "http://downloads.xiph.org/releases/ao/libao-${PV}.tar.gz"

inherit autotools pkgconfig

EXTRA_OECONF = "\
  --enable-oss \
  --disable-esd \
  --disable-esdtest \
  --disable-alsa \
  --enable-alsa09 \
  --disable-arts \
  --disable-nas \
  --disable-pulse \
"

PACKAGES =+ "${PN}-alsa ${PN}-alsa-dev ${PN}-pulse ${PN}-pulse-dev ${PN}-oss ${PN}-oss-dev"

FILES_${PN}-alsa = "${libdir}/ao/plugins-2/libalsa*.so"
FILES_${PN}-alsa-dev = "${libdir}/ao/plugins-2/libalsa*.la"
FILES_${PN}-pulse = "${libdir}/ao/plugins-2/libpulse*.so"
FILES_${PN}-pulse-dev = "${libdir}/ao/plugins-2/libpulse*.la"
FILES_${PN}-oss = "${libdir}/ao/plugins-2/liboss*.so"
FILES_${PN}-oss-dev = "${libdir}/ao/plugins-2/liboss*.la"

FILES_${PN} += "${libdir}/ao/plugins-2/*.so"
FILES_${PN}-dev += "${libdir}/ao/plugins-2/*.la"
FILES_${PN}-dbg += "${libdir}/ao/plugins-2/.debug"

SRC_URI[md5sum] = "b92cba3cbcf1ee9bc221118a85d23dcd"
SRC_URI[sha256sum] = "e52e05af6b10f42d2ee9845df1a581bf2b352060eabf7946aee0a600c3878954"
