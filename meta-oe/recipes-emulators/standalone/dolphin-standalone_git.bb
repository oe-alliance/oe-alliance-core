SUMMARY = "Dolphin standalone GameCube and Wii emulator"
DESCRIPTION = "Native fullscreen Dolphin NoGUI launcher for Dream One and Dream Two."
HOMEPAGE = "https://dolphin-emu.org/"

LICENSE = "GPL-2.0-or-later"
PR = "r8"
LIC_FILES_CHKSUM = "file://COPYING;md5=42dac715b05c09ace95c14324cdfd147"

# EmuELEC's current Amlogic-ng standalone baseline.  It is official Dolphin
# upstream plus small controller hotkey and OpenATV EGL compatibility patches.
SRC_URI = "gitsm://github.com/dolphin-emu/dolphin.git;protocol=https;branch=master \
           file://dolphin-emuelec-hotkeys.patch \
           file://dolphin-amlogic-fbdev-window.patch \
           file://dolphin-amlogic-egl-config.patch \
           file://dolphin-amlogic-single-surface.patch \
           file://openatv-dolphin-autoconfig \
           file://Dolphin.ini \
           file://GFX.ini \
           file://WiimoteNew.ini \
"
SRCREV = "f84df02055ab9610feec48e65648cac5a3c098fa"
PV = "2609+git${SRCPV}"

DEPENDS = "alsa-lib bzip2 libevdev libusb1 udev virtual/egl virtual/libgles2 zlib"

inherit cmake pkgconfig

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming-highperformance', '.*', '^$', d)}"

EXTRA_OECMAKE = " \
    -DBUILD_SHARED_LIBS=OFF \
    -DCMAKE_POLICY_VERSION_MINIMUM=3.5 \
    -DDISTRIBUTOR=OpenATV \
    -DENABLE_ALSA=ON \
    -DENABLE_ANALYTICS=OFF \
    -DENABLE_AUTOUPDATE=OFF \
    -DENABLE_EGL=ON \
    -DENABLE_FBDEV=ON \
    -DENABLE_LTO=OFF \
    -DENABLE_NOGUI=ON \
    -DENABLE_PULSEAUDIO=OFF \
    -DENABLE_QT=OFF \
    -DENABLE_TESTS=OFF \
    -DENABLE_VULKAN=OFF \
    -DENABLE_X11=OFF \
    -DENCODE_FRAMEDUMPS=OFF \
    -DTHREADS_PTHREAD_ARG=OFF \
    -DUSE_DISCORD_PRESENCE=OFF \
    -DUSE_MGBA=OFF \
    -DUSE_RETRO_ACHIEVEMENTS=OFF \
    -DUSE_UPNP=OFF \
"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${B}/Binaries/dolphin-emu-nogui ${D}${bindir}/dolphin-emu-nogui
    install -m 0755 ${UNPACKDIR}/openatv-dolphin-autoconfig ${D}${bindir}/openatv-dolphin-autoconfig

    install -d ${D}${datadir}/dolphin-emu/sys
    cp -R --no-preserve=ownership ${S}/Data/Sys/. ${D}${datadir}/dolphin-emu/sys/

    install -d ${D}${datadir}/dolphin-emu/openatv-defaults
    install -m 0644 ${UNPACKDIR}/Dolphin.ini ${D}${datadir}/dolphin-emu/openatv-defaults/Dolphin.ini
    install -m 0644 ${UNPACKDIR}/GFX.ini ${D}${datadir}/dolphin-emu/openatv-defaults/GFX.ini
    install -m 0644 ${UNPACKDIR}/WiimoteNew.ini ${D}${datadir}/dolphin-emu/openatv-defaults/WiimoteNew.ini
}

FILES:${PN} = " \
    ${bindir}/dolphin-emu-nogui \
    ${bindir}/openatv-dolphin-autoconfig \
    ${datadir}/dolphin-emu \
"
