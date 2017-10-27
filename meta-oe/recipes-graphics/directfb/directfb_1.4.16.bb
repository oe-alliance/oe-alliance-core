require directfb.inc

RV = "1.4-6"

DEPENDS += "sysfsutils"

EXTRA_OECONF = "\
  --enable-freetype=yes \
  --enable-zlib \
  --with-gfxdrivers=none \
  --disable-sdl \
  --disable-vnc \
  --disable-x11 \
"

LEAD_SONAME = "libdirectfb-1.4.so.6"

SRC_URI[md5sum] = "888e9b2e3d33a42c3c105d6551e06555"
SRC_URI[sha256sum] = "a1609713ae7c50f8ad7b94b2ad53ef90c0d82e66d17fe41c9b39403d809a9c6d"
