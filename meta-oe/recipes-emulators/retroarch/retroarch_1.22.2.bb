SUMMARY = "RetroArch libretro frontend for OpenATV RetroGaming"
DESCRIPTION = "RetroArch using machine-selected vendor Mali fbdev or Broadcom NXPL/V3D EGL/GLES2."
HOMEPAGE = "https://www.retroarch.com/"
BUGTRACKER = "https://github.com/libretro/RetroArch/issues"

LICENSE = "GPL-3.0-only"
PR = "r20"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

SRC_URI = "git://github.com/libretro/RetroArch.git;protocol=https;branch=master \
           file://0001-mali-fbdev-use-hisilicon-native-window.patch \
           file://0002-hisi-map-receiver-keys-to-retropad.patch \
           file://0003-hisi-route-volume-keys-to-hotkeys.patch \
           file://0004-hisi-cv200-force-gles2.patch \
           file://0005-bcm-nxpl-context.patch \
           file://bcm_nxpl_ctx.c \
           file://bcm_v3d_ctx.c \
           file://dream_vc5_ctx.c \
"
SRCREV = "69a4f0ea1e8aaf442ae4858f2e7f2b31a1776576"

# Classic Vu+ PCM can miss a POLLOUT wakeup with an empty playback buffer.
# Do not alter the already tested GigaBlue or Mali audio paths.
SRC_URI:append = "${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming-bcm-vuplus', ' file://0006-vuplus-alsa-bounded-write.patch', '', d)}"

B = "${S}"

DEPENDS = "alsa-lib udev virtual/egl virtual/libgles2 zlib"

inherit pkgconfig

# gb7252 covers incompatible old/Pro Nexus providers. Keep the frontend's
# generated shared-library dependencies in the model's existing opkg arch,
# otherwise one gb7252 IPK would overwrite the other model's feed package.
PACKAGE_ARCH = "${@d.getVar('MACHINEBUILD') if d.getVar('MACHINE') == 'gb7252' else d.getVar('MACHINE_ARCH')}"
COMPATIBLE_MACHINE = "${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming', '.*', '^$', d)}"

# HiSilicon 4K vendor EGL implementations use the same default native-window
# ABI as the working STB-Kodi backends.  Keep this explicit feature separate
# from generic RetroGaming machines so Amlogic and other GLES providers retain
# the upstream Mali fbdev window structure.
CFLAGS:append = "${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming-hisi4k', ' -DHAVE_HISI_STB=1', '', d)}${@' -DHAVE_HISI_CV200=1' if d.getVar('SOC_FAMILY') == 'hisi3798cv200' else ''}${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming-bcm-nxpl', ' -DHAVE_BCM_NXPL=1', '', d)}${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming-bcm-vuplus', ' -DHAVE_BCM_VUPLUS=1', '', d)}"
CFLAGS:append = "${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming-bcm-authenticated', ' -DHAVE_BCM_AUTHENTICATED=1', '', d)}"
CFLAGS:append = "${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming-bcm-nextv', ' -DHAVE_BCM_NEXTV=1', '', d)}"
CFLAGS:append = "${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming-bcm-dags', ' -DHAVE_BCM_DAGS=1', '', d)}"
CFLAGS:append = "${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming-bcm-v3d', ' -DHAVE_BCM_V3D=1', '', d)}"
CFLAGS:append = "${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming-dream-vc5', ' -DHAVE_DREAM_VC5=1', '', d)}"
# Some vendor GLES packages opt out of automatic shared-library dependencies.
# Let the machine specify their runtime package without disabling file-rdeps QA.
RETROARCH_GLES_RDEPENDS ??= ""
RDEPENDS:${PN} += "${RETROARCH_GLES_RDEPENDS}"
RDEPENDS:${PN}:append = "${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming-dream-vc5', ' retroarch-dm9x0-runtime', '', d)}"

# RetroArch's explicit NEON option adds ARM32-only compiler flags
# (-mfpu=neon -marm).  AArch64 provides Advanced SIMD as part of the
# architecture and must therefore use the normal compiler flags.
RETROARCH_NEON = "${@bb.utils.contains('TUNE_FEATURES', 'neon', '--enable-neon', '--disable-neon', d)}"

RETROARCH_OECONF = " \
    --enable-alsa \
    --enable-dylib \
    --enable-egl \
    --enable-mali_fbdev \
    ${RETROARCH_NEON} \
    --enable-networking \
    --enable-networkgamepad \
    --enable-opengles \
    --enable-rgui \
    --enable-threads \
    --enable-thread_storage \
    --enable-udev \
    --enable-zlib \
    --disable-al \
    --disable-bluetooth \
    --disable-dbus \
    --disable-discord \
    --disable-ffmpeg \
    --disable-freetype \
    --disable-gfx_widgets \
    --disable-hid \
    --disable-jack \
    --disable-kms \
    --disable-libusb \
    --disable-materialui \
    --disable-mpv \
    --disable-online_updater \
    --disable-opengl1 \
    --disable-opengl_core \
    --disable-oss \
    --disable-ozone \
    --disable-pipewire \
    --disable-pulse \
    --disable-qt \
    --disable-sdl \
    --disable-sdl2 \
    --disable-ssl \
    --disable-systemd \
    --disable-tinyalsa \
    --disable-update_assets \
    --disable-update_core_info \
    --disable-update_cores \
    --disable-v4l2 \
    --disable-vulkan \
    --disable-vulkan_display \
    --disable-wayland \
    --disable-x11 \
    --disable-xmb \
    --with-assets_dir=${datadir}/libretro/assets \
    --with-core_info_dir=${datadir}/libretro/info \
"

do_configure() {
    install -m 0644 ${UNPACKDIR}/bcm_nxpl_ctx.c ${S}/gfx/drivers_context/
    install -m 0644 ${UNPACKDIR}/bcm_v3d_ctx.c ${S}/gfx/drivers_context/
    install -m 0644 ${UNPACKDIR}/dream_vc5_ctx.c ${S}/gfx/drivers_context/
    export PKG_CONF_PATH="pkg-config"
    ./configure \
        --build=${BUILD_SYS} \
        --host=${HOST_SYS} \
        --prefix=${prefix} \
        --bindir=${bindir} \
        --sysconfdir=${sysconfdir} \
        ${RETROARCH_OECONF}
}

do_compile() {
    oe_runmake NEED_CXX_LINKER=1
}

do_install() {
    oe_runmake NEED_CXX_LINKER=1 DESTDIR=${D} install
    rm -rf ${D}${datadir}/applications ${D}${datadir}/icons ${D}${datadir}/metainfo
}
