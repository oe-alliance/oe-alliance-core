PACKAGE_ARCH = "${MACHINE_ARCH}"

EXTRA_OECONF = "\
  --enable-freetype=yes \
  ${@bb.utils.contains("MACHINE_FEATURES", "sdl", "--enable-sdl --disable-imlib2 --disable-mesa", "--disable-sdl", d)} \
  --enable-zlib \
  --with-gfxdrivers=none \
  --disable-vnc \
  --disable-x11 \
"

SRC_URI = "http://sources.buildroot.net/DirectFB-${PV}.tar.gz \
           file://directfb-1.2.x-fix-pkgconfig-cflags.patch \
           file://configurefix.patch"

do_rm_work() {
}
