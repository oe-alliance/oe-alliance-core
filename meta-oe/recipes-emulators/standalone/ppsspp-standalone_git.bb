SUMMARY = "PPSSPP standalone PSP emulator"
DESCRIPTION = "Native fullscreen PPSSPP launcher for high-performance Amlogic receivers."
HOMEPAGE = "https://www.ppsspp.org/"

LICENSE = "GPL-2.0-or-later"
PR = "r16"
LIC_FILES_CHKSUM = "file://LICENSE.TXT;md5=e336f8162cddec7981e240f46825d8a2"

# Pin the stable SDL2 release; device patches are checked against this tree.
# FFmpeg 8 compatibility is already included upstream in this release.
SRC_URI = "gitsm://github.com/hrydgard/ppsspp.git;protocol=https;branch=master \
           file://ppsspp-amlogic-fbdev-window.patch \
           file://ppsspp-amlogic-egl-config.patch \
           file://ppsspp-amlogic-opaque-alpha.patch \
           file://ppsspp-amlogic-hdmi-audio.patch \
           file://ppsspp-amlogic-no-sdl-video.patch \
           file://ppsspp-amlogic-window-config.patch \
           file://ppsspp-system-ffmpeg-optional-components.patch \
           file://ppsspp-sdl-disable-directfb-syswm.patch \
"
SRCREV = "fa50bb1976065c4f8b1b47af227d367fe9771555"
PV = "1.20.4+git${SRCPV}"

DEPENDS = "ffmpeg libpng libsdl2 libzip virtual/egl virtual/libgles2 zlib"

inherit cmake pkgconfig

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming-highperformance', '.*', '^$', d)}"

CXXFLAGS:append = " -DOPENATV_AMLOGIC_FBDEV=1"

EXTRA_OECMAKE = " \
    -DARM64=ON \
    -DARM_NO_VULKAN=ON \
    -DHEADLESS=OFF \
    -DUNITTEST=OFF \
    -DUSING_EGL=ON \
    -DUSING_FBDEV=ON \
    -DUSING_GLES2=ON \
    -DUSING_X11_VULKAN=OFF \
    -DUSE_WAYLAND_WSI=OFF \
    -DUSE_VULKAN_DISPLAY_KHR=OFF \
    -DUSE_DISCORD=OFF \
    -DUSE_MINIUPNPC=OFF \
    -DUSE_SYSTEM_FFMPEG=ON \
    -DUSE_SYSTEM_LIBPNG=ON \
    -DUSE_SYSTEM_LIBSDL2=ON \
    -DUSE_SYSTEM_LIBZIP=ON \
"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${B}/PPSSPPSDL ${D}${bindir}/PPSSPPSDL

    install -d ${D}${datadir}/ppsspp/assets
    cp -R --no-preserve=ownership ${S}/assets/. ${D}${datadir}/ppsspp/assets/
    ln -s ../share/ppsspp/assets ${D}${bindir}/assets
}

FILES:${PN} = " \
    ${bindir}/PPSSPPSDL \
    ${bindir}/assets \
    ${datadir}/ppsspp/assets \
"
