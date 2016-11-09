require directfb.inc

RV = "1.4-6"
PR = "r6"

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

SRC_URI[md5sum] = "c0cd41476ff7bf72b87c6a41c6d260d1"
SRC_URI[sha256sum] = "13d5638b232d26089dc06233e3b806874c4d94d77dfd5e928ed0749824855f17"
