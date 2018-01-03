FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

DEPENDS += " ${@bb.utils.contains("MACHINE_FEATURES", "sdl", "libsdl", "", d)} ${@bb.utils.contains("TARGET_ARCH", "sh4", "fulan-dvb-modules-${MACHINE} stlirc", "", d)}"


EXTRA_OECONF = " \
  --enable-freetype=yes \
  --with-gfxdrivers=none \
  ${@bb.utils.contains("MACHINE_FEATURES", "sdl", "--enable-sdl --disable-imlib2 --disable-mesa", "--disable-sdl", d)} \
  --enable-zlib \
  --disable-vnc \
  --disable-x11 \
  ${@bb.utils.contains("TARGET_ARCH", "sh4", "--enable-stmfbdev=yes --enable-mme=yes --enable-hwjpeg --enable-rle --enable-hwpng", "", d)} \
"

FILES_${PN} += "\
  ${libdir}/directfb-${RV}/gfxdrivers/*.so \
"

FILES_${PN}-dev += "\
  ${libdir}/directfb-${RV}/gfxdrivers/*.la \
"
