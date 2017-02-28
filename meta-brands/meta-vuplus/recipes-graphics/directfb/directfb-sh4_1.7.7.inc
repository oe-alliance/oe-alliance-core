SRC_URI_append_sh4 = " \
    file://DirectFB-1.7.7.stm.fixed.patch;patch=1 \
"

EXTRA_OECONF = "\
  --enable-freetype \
  ${@bb.utils.contains("MACHINE_FEATURES", "sdl", "--enable-sdl --disable-imlib2 --disable-mesa", "--disable-sdl", d)} \
  --enable-zlib \
  --with-gfxdrivers=none \
  --disable-vnc \
  --disable-x11 \
  ${@bb.utils.contains("TARGET_ARCH", "sh4", "--enable-stmfbdev=yes --enable-mme=yes", "", d)} \
"

