PACKAGE_ARCH = "${MACHINE_ARCH}"

EXTRA_OECONF = "\
  --enable-freetype=yes \
  ${@base_contains("MACHINE_FEATURES", "sdl", "--enable-sdl --disable-imlib2 --disable-mesa", "--disable-sdl", d)} \
  --enable-zlib \
  --with-gfxdrivers=none \
  --disable-vnc \
  --disable-x11 \
"
