require directfb.inc

RV = "1.4-6"
PR = "r7"

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
SRC_URI[sha256sum] = "13d5638b232d26089dc06233e3b806874c4d94d77dfd5e928ed0749824855f17"
