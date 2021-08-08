FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}-${PV}:"

DEPENDS += " ${@bb.utils.contains("MACHINE_FEATURES", "sdl", "libsdl", "", d)} ${@bb.utils.contains("TARGET_ARCH", "sh4", "fulan-dvb-modules-${MACHINE} stlirc", "", d)}"

SRC_URI:append:sh4 = " \
    file://DirectFB-1.7.7.stm.fixed.patch;patch=1 \
"

EXTRA_OECONF = " \
  --enable-freetype=yes \
  --with-gfxdrivers=none \
  ${@bb.utils.contains("MACHINE_FEATURES", "sdl", "--enable-sdl --disable-imlib2 --disable-mesa", "--disable-sdl", d)} \
  --enable-zlib \
  --disable-vnc \
  --disable-x11 \
  ${@bb.utils.contains("TARGET_ARCH", "sh4", "--enable-stmfbdev=yes --enable-mme=yes --enable-hwjpeg --enable-rle --enable-hwpng", "", d)} \
"

FILES:${PN} += "\
  ${libdir}/directfb-${RV}/gfxdrivers/*.so \
"

FILES:${PN}-dev += "\
  ${libdir}/directfb-${RV}/gfxdrivers/*.la \
"
