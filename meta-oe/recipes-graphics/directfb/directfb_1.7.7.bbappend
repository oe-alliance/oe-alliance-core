FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PACKAGE_ARCH = "${MACHINE_ARCH}"

RREPLACES_${PN} += "stm-directfb"
RCONFLICTS_${PN} += "stm-directb"

DEPENDS += " \
    ${@base_contains("BRAND_OEM", "fulan", "fulan-dvb-modules" , "", d)} \
"

SRC_URI_append_sh4 = " \
    file://DirectFB-1.7.7.stm.fixed.patch;patch=1 \
"

EXTRA_OECONF = "\
  --enable-freetype=yes \
  ${@base_contains("MACHINE_FEATURES", "sdl", "--enable-sdl=yes --enable-imlib2=no --enable-mesa=no", "--enable-sdl=no", d)} \
  --enable-zlib=yes \
  --with-gfxdrivers=none \
  --enable-vnc=no \
  --enable-x11=no \
  ${@base_contains("TARGET_ARCH", "sh4", "--enable-stmfbdev=yes --enable-mme=yes", "", d)} \
"
