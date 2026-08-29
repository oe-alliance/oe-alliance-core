SUMMARY = "Kodi Media Center"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=7b423f1c9388eae123332e372451a4f7"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}-22:"

PACKAGE_ARCH = "${MACHINE}"

inherit ccache cmake gettext pkgconfig python3targetconfig

DREAM_AMLOGIC_STB = "${@'1' if d.getVar('MACHINE') in ('dreamone', 'dreamtwo') and d.getVar('SOC_FAMILY') == 'meson64' else '0'}"

DEPENDS += " \
            autoconf-native automake-native \
            fmt \
            flatbuffers flatbuffers-native \
            fstrcmp \
            rapidjson \
            crossguid \
            libudfread \
            ffmpeg \
            git-native \
            curl-native \
            gperf-native \
            jsonschemabuilder-native \
            meson-native \
            ninja-native \
            nasm-native \
            swig-native \
            unzip-native \
            nasm-native \
            zip-native \
            \
            avahi \
            bzip2 \
            curl \
            exiv2 \
            libdcadec \
            faad2 \
            fontconfig \
            fribidi \
            glib-2.0 \
            giflib \
            libass \
            libcdio \
            libcec \
            libinput \
            libbluray \
            libmicrohttpd \
            libnfs \
            libpcre \
            libplist \
            libsquish \
            libssh \
            spdlog \
            libtinyxml \
            libtinyxml2 \
            libxkbcommon \
            libxml2 \
            libxslt \
            util-linux-libuuid \
            lzo \
            pcre2 \
            nlohmann-json \
            mpeg2dec \
            openssl \
            python3 \
            samba \
            sqlite3 \
            taglib \
            virtual/egl \
            wavpack \
            zlib \
            texturepacker-native \
            ${@'libamcodec libstbplayer' if d.getVar('DREAM_AMLOGIC_STB') == '1' else 'libstbplayer'} \
            ${@'libvupl' if d.getVar('VUPLUS_MIPSEL_STB') == '1' else ''} \
          "
inherit gitpkgv
# 22.0 Piers Beta 1, current upstream master HEAD (2026-08-28)
SRCREV = "429fdbdf6f8bae1be73c41b623ba55e2eb4892b0"

# 'patch' doesn't support binary diffs
PATCHTOOL = "git"

PR = "r103"

PV = "22.0+gitr"
# Keep package upgrades monotonic when the pinned master revision advances.
# gitpkgv expands this to the commit count plus abbreviated source revision.
PKGV = "22.0+git${GITPKGV}"
PV_groovy = "4.0.27"
PV_commons-lang3 = "3.20.0"
PV_commons-text = "1.15.0"

SRC_URI[groovy.sha256sum] = "bc917c8bb01b2832f124a7bd63a3c72ba5e83ef7f056650dfd9a2f7944960685"
SRC_URI[commons-lang.sha256sum] = "a77875dbc8b7b687e49d914cf00cf7237a548f4163c2a64565b3da999d8b024f"
SRC_URI[commons-text.sha256sum] = "af36d019def06a31b4d5accf60b13c4de817ec8569af1ffb410eb5ab16b39721"
SRC_URI[libdvdcss.sha256sum] = "f204a9d8ac8a8414095d556373e5af9b95bb7cc72bf1467d936a48c961e8c474"
SRC_URI[libdvdread.sha256sum] = "b69f74d9ceea1ed173b579deba99f669c2cb42f3fd06d7d23b33ff222aa63763"
SRC_URI[libdvdnav.sha256sum] = "1363cdfaf6e92c0b574579299b5480f5867fb32989451468a28f3f402ec48787"

SRC_URI = "git://github.com/xbmc/xbmc.git;protocol=https;branch=master \
           https://archive.apache.org/dist/groovy/${PV_groovy}/distribution/apache-groovy-binary-${PV_groovy}.zip;name=groovy \
           https://dlcdn.apache.org/commons/lang/binaries/commons-lang3-${PV_commons-lang3}-bin.tar.gz;name=commons-lang \
           https://dlcdn.apache.org/commons/text/binaries/commons-text-${PV_commons-text}-bin.tar.gz;name=commons-text \
           https://mirrors.kodi.tv/build-deps/sources/libdvdcss-1.5.0.tar.bz2;name=libdvdcss;downloadfilename=libdvdcss.tar.bz2;unpack=0 \
           https://mirrors.kodi.tv/build-deps/sources/libdvdread-7.0.1.tar.bz2;name=libdvdread;downloadfilename=libdvdread.tar.bz2;unpack=0 \
           https://mirrors.kodi.tv/build-deps/sources/libdvdnav-7.0.0.tar.bz2;name=libdvdnav;downloadfilename=libdvdnav.tar.bz2;unpack=0 \
           file://0001-flatbuffers-22.patch \
           file://0002-readd-Touchscreen-settings.patch \
           file://0003-shader-nopow-22.patch \
           file://0004-stb-settings-22.patch \
           file://0005-stb-support-22.patch \
           file://0006-add-winsystemfactory-windowing-init.patch \
           file://0007-adapt-window-system-registration.patch \
           file://0008-reinstate-system-h.patch \
           file://0009-reinstate-platform-defines.patch \
           file://0010-older-gles.patch \
           file://0011-FindSmbClient-dont-use-pkgconfig-includedir.patch \
           file://0012-sigintfix-and-libinput-fix.patch \
           file://0013-texturepacker-dont-build-internal.patch \
           file://0014-older-gl.patch \
           file://0015-update-LinuxInputDevices.patch \
           file://0016-libdvd-meson-pkgconfig-sysroot.patch \
           file://0040-meson-cross-mips-cpu-family.patch \
           file://0017-older-gles2-format-enums.patch \
           file://0018-stb-libinput-evdev-key-mapping.patch \
           file://0019-stb-use-native-libinput-input-stack.patch \
           file://0020-stb-mali-configurable-osd-resolution.patch \
           file://0023-stb-hisi-lock-initial-egl-mode.patch \
           file://0024-dllloader-call-real-dlopen.patch \
           file://0025-opengles-allow-forcing-gles2.patch \
           file://0026-smb-default-to-smb2-02.patch \
           file://0028-stb-kodi22-resolution-model.patch \
           file://0052-stb-force-profile-on-media-hdd.patch \
           file://0103-native-stbplayer-codec.patch \
           file://kodi-stb-wrapper \
           file://kodi-stb-runtime \
           "

SRC_URI:append:aarch64 = " file://widevine-aarch64-atomic.S"

# All supported HiSilicon machine configurations use an SOC_FAMILY beginning
# with "hisi", while their MACHINE_FEATURES names are not consistent (hisi,
# hisil, or hisil-<chip>).  Use the SoC family as the single platform selector.
HISI_STB = "${@'1' if (d.getVar('SOC_FAMILY') or '').startswith('hisi') else '0'}"
HISI_CV200_STB = "${@'1' if d.getVar('SOC_FAMILY') == 'hisi3798cv200' else '0'}"
BCM_DVB_STB = "${@'1' if (d.getVar('SOC_FAMILY') or '').startswith('bcm') and d.getVar('TARGET_ARCH') in ('arm', 'mipsel') else '0'}"
VUPLUS_ARM_STB = "${@'1' if d.getVar('BRAND_OEM') == 'vuplus' and d.getVar('TARGET_ARCH') == 'arm' else '0'}"
VUPLUS_MIPSEL_STB = "${@'1' if d.getVar('BRAND_OEM') == 'vuplus' and d.getVar('TARGET_ARCH') == 'mipsel' else '0'}"
VUPLUS_STB = "${@'1' if d.getVar('BRAND_OEM') == 'vuplus' and d.getVar('TARGET_ARCH') in ('arm', 'mipsel') else '0'}"
VUPLUS_DUO4K_LOCALE_FALLBACK = "${@'1' if d.getVar('MACHINE') in ('vuduo4k', 'vuduo4kse') else '0'}"
GIGABLUE_NXPL_ARM_STB = "${@'1' if d.getVar('MACHINE') in ('gb7252', 'gb72604') and d.getVar('TARGET_ARCH') == 'arm' else '0'}"
BRCM_NXPL_ARM_STB = "${@'1' if d.getVar('VUPLUS_ARM_STB') == '1' or d.getVar('GIGABLUE_NXPL_ARM_STB') == '1' else '0'}"
GB_PLATFORM_NXPL_STB = "${@'1' if d.getVar('MACHINE') in ('gb7252', 'gb72604', 'vuduo4klite') and d.getVar('TARGET_ARCH') == 'arm' else '0'}"
V3DNXPL_STB = "${@'1' if 'v3d-nxpl' in (d.getVar('MACHINE_FEATURES') or '').split() or (d.getVar('TARGET_ARCH') == 'mipsel' and d.getVar('MACHINE') in ('triplex', 'formuler1')) else '0'}"
XCORE_MIPSEL_STB = "${@'1' if d.getVar('BRAND_OEM') == 'xcore' and d.getVar('TARGET_ARCH') == 'mipsel' else '0'}"
DREAM_BCM_STB = "${@'1' if d.getVar('MACHINE') in ('dm7080', 'dm820', 'dm900', 'dm920') else '0'}"
DREAM_DM9X0_STB = "${@'1' if d.getVar('MACHINE') in ('dm900', 'dm920') and d.getVar('TARGET_ARCH') == 'arm' else '0'}"
DREAM_MIPSEL_STB = "${@'1' if d.getVar('MACHINE') in ('dm7080', 'dm820') and d.getVar('TARGET_ARCH') == 'mipsel' else '0'}"

SRC_URI:append = "${@' file://kodi-hisi-wrapper file://kodi-hisi-appliance.xml file://0051-hisi-alsa-fast-sink-switch.patch' if d.getVar('HISI_STB') == '1' else ''}"
SRC_URI:append = "${@' file://kodi-bcm-wrapper' if d.getVar('BCM_DVB_STB') == '1' else ''}"
SRC_URI:append = "${@' file://kodi-dream-aml-wrapper' if d.getVar('DREAM_AMLOGIC_STB') == '1' else ''}"
SRC_URI:append = "${@' file://0027-bcm-alsa-hard-recovery.patch' if d.getVar('BCM_DVB_STB') == '1' else ''}"
SRC_URI:append = "${@' file://0029-vuplus-arm-runtime-nxpl.patch' if d.getVar('BRCM_NXPL_ARM_STB') == '1' else ''}"
SRC_URI:append = "${@' file://0047-vuplus-arm-force-runtime-gles2.patch' if d.getVar('BRCM_NXPL_ARM_STB') == '1' else ''}"
SRC_URI:append = "${@' file://0030-vuplus-alsa-nonblocking-sink-switch.patch' if d.getVar('VUPLUS_MIPSEL_STB') == '1' or d.getVar('BRCM_NXPL_ARM_STB') == '1' else ''}"
SRC_URI:append = "${@' file://0031-vuplus-arm-proc-video-modes.patch' if d.getVar('BRCM_NXPL_ARM_STB') == '1' else ''}"
SRC_URI:append = "${@' file://0053-gigablue-nxpl-lifecycle.patch' if d.getVar('GB_PLATFORM_NXPL_STB') == '1' else ''}"
SRC_URI:append = " file://0032-vuplus-dvb-master-volume.patch"
SRC_URI:append = "${@' file://0033-hisi-hifb-proc-video-modes.patch' if d.getVar('HISI_STB') == '1' else ''}"
SRC_URI:append = "${@' file://0050-hisi-cv200-force-runtime-gles2.patch' if d.getVar('HISI_CV200_STB') == '1' else ''}"
SRC_URI:append = "${@' file://0034-v3d-nxpl-proc-video-modes.patch' if d.getVar('V3DNXPL_STB') == '1' else ''}"
SRC_URI:append = "${@' file://0045-v3d-nxpl-gui-above-video.patch' if d.getVar('V3DNXPL_STB') == '1' else ''}"
SRC_URI:append = "${@' file://0046-v3d-nxpl-stable-gui-surface.patch' if d.getVar('V3DNXPL_STB') == '1' else ''}"
SRC_URI:append = "${@' file://0035-dreambox-dm9x0-gles-init.patch file://0036-dreambox-dm9x0-alsa-nonblocking-open.patch file://0037-dreambox-cap-hdmi-mode.patch' if d.getVar('DREAM_BCM_STB') == '1' else ''}"
SRC_URI:append = "${@' file://patch-dm9x0-vc5-query.py' if d.getVar('DREAM_DM9X0_STB') == '1' else ''}"
SRC_URI:append = "${@' file://0038-vuplus-mips-libvupl-egl.patch' if d.getVar('VUPLUS_MIPSEL_STB') == '1' else ''}"
SRC_URI:append = "${@' file://0044-vuplus-mips-refresh-only-mode-switch.patch' if d.getVar('VUPLUS_MIPSEL_STB') == '1' else ''}"
SRC_URI:append = "${@' file://kodi-vuplus-duo4k-advancedsettings.xml' if d.getVar('VUPLUS_DUO4K_LOCALE_FALLBACK') == '1' else ''}"
SRC_URI:append = "${@' file://0039-xcore-mips-v3d-platform.patch' if d.getVar('XCORE_MIPSEL_STB') == '1' else ''}"
SRC_URI:append = "${@' file://0042-dreambox-mips-refresh-only-mode-switch.patch' if d.getVar('DREAM_MIPSEL_STB') == '1' else ''}"
SRC_URI:append = "${@' file://0043-dreambox-mips-alsa-nonblocking-sink-switch.patch' if d.getVar('DREAM_MIPSEL_STB') == '1' else ''}"
SRC_URI:append = " file://0049-dream-amlogic-audio-stream-state.patch"
SRC_URI:append = "${@' file://0041-amlogic-meson64-native-codec.patch file://0048-dream-amlogic-hdmi-modes.patch file://amlogic/AMLCodec.cpp file://amlogic/AMLCodec.h file://amlogic/DVDVideoCodecAmlogic.cpp file://amlogic/DVDVideoCodecAmlogic.h file://amlogic/RendererAML.cpp file://amlogic/RendererAML.h file://amlogic/AMLUtils.cpp file://amlogic/AMLUtils.h' if d.getVar('DREAM_AMLOGIC_STB') == '1' else ''}"

ACCEL ?= ""
ACCEL:x86 = "vaapi vdpau"
ACCEL:x86-64 = "vaapi vdpau"

# Default to GBM everywhere, sucks to be nvidia
WINDOWSYSTEM ?= "stb"

#[cmake] only use APP_RENDER_SYSTEM
#https://github.com/xbmc/xbmc/commit/d159837cf736c9ba17772ba52e4ce95aa3625528
APPRENDERSYSTEM ?= "gles"

#TOOLCHAIN:arm ?= "clang"

PACKAGECONFIG ?= "${ACCEL} ${WINDOWSYSTEM} pulseaudio samba nfs lcms lto \
                   ${@bb.utils.contains('TOOLCHAIN', 'clang', 'clang', '', d)} \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-lld', 'lld', '', d)} \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'opengl', 'openglesv2', d)}"

# Core windowing system choices

PACKAGECONFIG[gbm] = "-DCORE_PLATFORM_NAME=gbm -DGBM_RENDER_SYSTEM=gles,,"
PACKAGECONFIG[stb] = "-DCORE_PLATFORM_NAME=stb,,"
PACKAGECONFIG[raspberrypi] = "-DCORE_PLATFORM_NAME=rbpi,,userland"
PACKAGECONFIG[amlogic] = "-DCORE_PLATFORM_NAME=aml,,"
PACKAGECONFIG[wayland] = "-DCORE_PLATFORM_NAME=wayland -DWAYLAND_RENDER_SYSTEM=gles,,wayland waylandpp"

PACKAGECONFIG[opengl] = "-DENABLE_OPENGL=ON,,"
PACKAGECONFIG[openglesv2] = "-DENABLE_GLES=ON,,virtual/egl"

PACKAGECONFIG[vaapi] = "-DENABLE_VAAPI=ON,-DENABLE_VAAPI=OFF,libva"
PACKAGECONFIG[vdpau] = "-DENABLE_VDPAU=ON,-DENABLE_VDPAU=OFF,libvdpau"
PACKAGECONFIG[mysql] = "-DENABLE_MYSQLCLIENT=ON,-DENABLE_MYSQLCLIENT=OFF,mysql5"
PACKAGECONFIG[pulseaudio] = "-DENABLE_PULSEAUDIO=ON,-DENABLE_PULSEAUDIO=OFF,pulseaudio"
PACKAGECONFIG[samba] = "-DENABLE_SMBCLIENT=ON,-DENABLE_SMBCLIENT=OFF,samba"
PACKAGECONFIG[nfs] = "-DENABLE_NFS=ON,-DENABLE_NFS=OFF,libnfs"
PACKAGECONFIG[lcms] = ",,lcms"

# Compilation tunes

PACKAGECONFIG[lld] = "-DENABLE_LLD=ON,-DENABLE_LLD=OFF,llvm"
PACKAGECONFIG[clang] = "-DENABLE_CLANGFORMAT=ON -DENABLE_CLANGTIDY=ON,-DENABLE_CLANGFORMAT=OFF -DENABLE_CLANGTIDY=OFF,llvm"
PACKAGECONFIG[gold] = "-DENABLE_GOLD=ON,-DENABLE_GOLD=OFF"
PACKAGECONFIG[lto] = "-DUSE_LTO=${@oe.utils.cpu_count()},-DUSE_LTO=OFF"

CFLAGS += "-lssl -lcrypto -lz"
CXXFLAGS:append:mipsarch = " -latomic"
CXXFLAGS:append = "${@' -DTARGET_HISI_CV200' if d.getVar('HISI_CV200_STB') == '1' else ''}"
LDFLAGS += "${TOOLCHAIN_OPTIONS}"
LDFLAGS:append:mipsarch = " -latomic"
EXTRA_OECMAKE:append:mipsarch = " -DWITH_ARCH=${TARGET_ARCH}"
EXTRA_OECMAKE:append = "${@' -DOPENGLES_FORCE_GLES2=ON' if d.getVar('VUPLUS_STB') == '1' or d.getVar('GIGABLUE_NXPL_ARM_STB') == '1' or d.getVar('HISI_CV200_STB') == '1' else ''}"

KODI_DISABLE_INTERNAL_LIBRARIES = " \
  -DENABLE_INTERNAL_CROSSGUID=OFF \
  -DENABLE_INTERNAL_FLATBUFFERS=OFF \
  -DENABLE_INTERNAL_FMT=OFF \
  -DENABLE_INTERNAL_FSTRCMP=0 \
  -DENABLE_INTERNAL_RapidJSON=OFF \
  -DENABLE_INTERNAL_SPDLOG=OFF \
  -DENABLE_INTERNAL_FFMPEG=OFF \
"

# Allow downloads during internals build
do_compile[network] = "1"

#RUNTIME:arm ?= "llvm"

RUNTIME_NM = "${@bb.utils.contains('RUNTIME', 'llvm', '${TARGET_PREFIX}llvm-nm', '${TARGET_PREFIX}gcc-nm', d)}"

EXTRA_OECMAKE = " \
    ${KODI_ARCH} \
    ${KODI_DISABLE_INTERNAL_LIBRARIES} \
    -DAPP_RENDER_SYSTEM=${APPRENDERSYSTEM} \
    \
    -DJava_JAVA_EXECUTABLE=/usr/bin/java \
    -DCLANG_TIDY_EXECUTABLE=${STAGING_BINDIR_NATIVE}/clang-tidy \
    -DCLANG_FORMAT_EXECUTABLE=${STAGING_BINDIR_NATIVE}/clang-format \
    \
    -DWITH_TEXTUREPACKER=${STAGING_BINDIR_NATIVE}/TexturePacker \
    -DWITH_JSONSCHEMABUILDER=${STAGING_BINDIR_NATIVE}/JsonSchemaBuilder \
    \
    -DENABLE_STATIC_LIBS=FALSE \
    -DCMAKE_NM=${STAGING_BINDIR_NATIVE}/${TARGET_SYS}/${RUNTIME_NM} \
    \
    -DFFMPEG_PATH=${RECIPE_SYSROOT}${prefix} \
    -DLIBDVD_INCLUDE_DIR=${STAGING_INCDIR} \
    -DNFS_INCLUDE_DIR=${STAGING_INCDIR} \
    -DSHAIRPLAY_INCLUDE_DIR=${STAGING_INCDIR} \
    \
    -DENABLE_AIRTUNES=ON \
    -DENABLE_STBPLAYER=ON \
    -DENABLE_OPTICAL=OFF \
    -DENABLE_DVDCSS=OFF \
    -DENABLE_DEBUGFISSION=OFF \
    -DCMAKE_BUILD_TYPE=RelWithDebInfo \
    -Dgroovy_SOURCE_DIR=${UNPACKDIR}/groovy-${PV_groovy} \
    -Dapache-commons-lang_SOURCE_DIR=${UNPACKDIR}/commons-lang3-${PV_commons-lang3} \
    -Dapache-commons-text_SOURCE_DIR=${UNPACKDIR}/commons-text-${PV_commons-text} \
    -DLIBDVDNAV_URL=${UNPACKDIR}/libdvdnav.tar.bz2 \
    -DLIBDVDREAD_URL=${UNPACKDIR}/libdvdread.tar.bz2 \
    -DLIBDVDCSS_URL=${UNPACKDIR}/libdvdcss.tar.bz2 \
"

OECMAKE_GENERATOR = "Unix Makefiles"
# PARALLEL_MAKE = " "

FULL_OPTIMIZATION:armv7a = "-fomit-frame-pointer -O3 -ffast-math"
FULL_OPTIMIZATION:armv7ve = "-fomit-frame-pointer -O3 -ffast-math"
BUILD_OPTIMIZATION = "${FULL_OPTIMIZATION}"

# for python modules
export HOST_SYS
export BUILD_SYS
export STAGING_LIBDIR
export STAGING_INCDIR
export ${PYTHON_DIR}

export TARGET_PREFIX

do_configure:prepend() {
    if [ "${DREAM_AMLOGIC_STB}" = "1" ]; then
        install -d ${S}/xbmc/cores/VideoPlayer/DVDCodecs/Video
        install -d ${S}/xbmc/cores/VideoPlayer/VideoRenderers/HwDecRender
        install -d ${S}/xbmc/utils
        install -m 0644 ${UNPACKDIR}/amlogic/AMLCodec.cpp ${S}/xbmc/cores/VideoPlayer/DVDCodecs/Video/
        install -m 0644 ${UNPACKDIR}/amlogic/AMLCodec.h ${S}/xbmc/cores/VideoPlayer/DVDCodecs/Video/
        install -m 0644 ${UNPACKDIR}/amlogic/DVDVideoCodecAmlogic.cpp ${S}/xbmc/cores/VideoPlayer/DVDCodecs/Video/
        install -m 0644 ${UNPACKDIR}/amlogic/DVDVideoCodecAmlogic.h ${S}/xbmc/cores/VideoPlayer/DVDCodecs/Video/
        install -m 0644 ${UNPACKDIR}/amlogic/RendererAML.cpp ${S}/xbmc/cores/VideoPlayer/VideoRenderers/HwDecRender/
        install -m 0644 ${UNPACKDIR}/amlogic/RendererAML.h ${S}/xbmc/cores/VideoPlayer/VideoRenderers/HwDecRender/
        install -m 0644 ${UNPACKDIR}/amlogic/AMLUtils.cpp ${S}/xbmc/utils/
        install -m 0644 ${UNPACKDIR}/amlogic/AMLUtils.h ${S}/xbmc/utils/
    fi

    # Ensure 'nm' can find the lto plugins
    liblto=$(find ${STAGING_DIR_NATIVE} -name "liblto_plugin.so.0.0.0")
    mkdir -p ${STAGING_LIBDIR_NATIVE}/bfd-plugins
    ln -sf $liblto ${STAGING_LIBDIR_NATIVE}/bfd-plugins/liblto_plugin.so

#    sed -i -e 's:CMAKE_NM}:}${TARGET_PREFIX}gcc-nm:' ${S}/xbmc/cores/DllLoader/exports/CMakeLists.txt
}

do_configure:append() {
    sed -i '\|^kodi-stb: /usr/lib/libuuid\.so$|d' ${B}/CMakeFiles/kodi.dir/build.make
}

do_compile:append:aarch64() {
    ${CC} ${CFLAGS} ${LDFLAGS} -shared -nostdlib \
        -Wl,-soname,libwidevine-aarch64-atomic.so \
        -o ${B}/libwidevine-aarch64-atomic.so \
        ${UNPACKDIR}/widevine-aarch64-atomic.S
}

do_install:append() {
    # Kodi scans this binary add-on location even when no binary add-ons are
    # installed.  Keep the empty directory in the base package to avoid a
    # recurring GetDirectory error on every add-on scan.
    install -d ${D}${libdir}/kodi/addons
    install -m 0755 ${UNPACKDIR}/kodi-stb-runtime ${D}${libdir}/kodi/kodi-stb-runtime.sh

    if [ -f ${B}/libwidevine-aarch64-atomic.so ]; then
        install -m 0755 ${B}/libwidevine-aarch64-atomic.so \
            ${D}${libdir}/kodi/libwidevine-aarch64-atomic.so
    fi

    if [ "${DREAM_AMLOGIC_STB}" = "1" ]; then
        mv ${D}${bindir}/kodi ${D}${bindir}/kodi.real
        install -m 0755 ${UNPACKDIR}/kodi-dream-aml-wrapper ${D}${bindir}/kodi
    elif [ "${HISI_STB}" = "1" ]; then
        mv ${D}${bindir}/kodi ${D}${bindir}/kodi.real
        install -m 0755 ${UNPACKDIR}/kodi-hisi-wrapper ${D}${bindir}/kodi
        install -d ${D}${datadir}/kodi/system/settings
        install -m 0644 ${UNPACKDIR}/kodi-hisi-appliance.xml \
            ${D}${datadir}/kodi/system/settings/appliance.xml
    elif [ "${BCM_DVB_STB}" = "1" ]; then
        mv ${D}${bindir}/kodi ${D}${bindir}/kodi.real
        install -m 0755 ${UNPACKDIR}/kodi-bcm-wrapper ${D}${bindir}/kodi
    else
        mv ${D}${bindir}/kodi ${D}${bindir}/kodi.real
        install -m 0755 ${UNPACKDIR}/kodi-stb-wrapper ${D}${bindir}/kodi
    fi

    if [ "${DREAM_DM9X0_STB}" = "1" ]; then
        # libvc5dream 1.0.5 crashes when bcmSchedQuery receives the permitted
        # finalized_deps == NULL form.  Generate a hash-checked private Kodi
        # copy; never replace the system library used by Enigma2.
        install -d ${D}${libdir}/kodi/dm9x0
        ${PYTHON} ${UNPACKDIR}/patch-dm9x0-vc5-query.py \
            ${RECIPE_SYSROOT}${libdir}/libvc5dream.so.1.0.0 \
            ${D}${libdir}/kodi/dm9x0/libvc5dream.so.1
        chmod 0755 ${D}${libdir}/kodi/dm9x0/libvc5dream.so.1
    fi

    if [ "${VUPLUS_DUO4K_LOCALE_FALLBACK}" = "1" ]; then
        # The Duo4K/SE runtime crashes inside the platform wchar_t collation
        # facet while Kodi sorts its keymaps.  Use Kodi's internal Unicode
        # accent-folding collation instead of hiding the SIGSEGV with the old
        # AlphaNumericCompare try/catch patch.
        install -d ${D}${datadir}/kodi/system
        install -m 0644 ${UNPACKDIR}/kodi-vuplus-duo4k-advancedsettings.xml \
            ${D}${datadir}/kodi/system/advancedsettings.xml
    fi
}

INSANE_SKIP:${PN} = "rpaths already-stripped textrel installed-vs-shipped"
INSANE_SKIP = "src-uri-bad"

FILES:${PN} = "${libdir}/kodi ${libdir}/xbmc"
FILES:${PN} += "${bindir}/kodi ${bindir}/xbmc ${bindir}/kodi-TexturePacker"
FILES:${PN} += "${datadir}/icons ${datadir}/kodi ${datadir}/xbmc ${datadir}/applications"
FILES:${PN} += "${bindir}/kodi-standalone ${bindir}/xbmc-standalone ${datadir}/xsessions ${datadir}/metainfo"
FILES:${PN} += "${bindir}/kodi.real"
FILES:${PN} += "${libdir}/firewalld"
FILES:${PN}-dev = "${includedir}"

RDEPENDS:${PN} += "${@'libamcodec libstbplayer' if d.getVar('DREAM_AMLOGIC_STB') == '1' else 'libstbplayer'} \
    ${@'libstbplayer-backend-hisi-dvb' if d.getVar('HISI_STB') == '1' else ''} \
    ${@'libstbplayer-backend-bcm-dvb' if d.getVar('BCM_DVB_STB') == '1' else ''} \
    ${@'libstbplayer-backend-dream-aml' if d.getVar('DREAM_AMLOGIC_STB') == '1' else ''} \
    kodi-addon-script-xbmc-lcdproc \
    stb-lcdd \
    xkeyboard-config \
"
RDEPENDS:${PN}:append:libc-glibc = " glibc-gconv-unicode glibc-gconv-utf-32"
FILES:${PN}-dbg += "${libdir}/kodi/.debug ${libdir}/kodi/*/.debug ${libdir}/kodi/*/*/.debug ${libdir}/kodi/*/*/*/.debug"

# kodi uses some kind of dlopen() method for libcec so we need to add it manually
# OpenGL builds need glxinfo, that's in mesa-demos
RRECOMMENDS:${PN}:append = " libcec \
                             libcurl \
                             libnfs \
                             nss \
                             os-release \
                             ${PYTHON_PN} \
                             ${PYTHON_PN}-ctypes \
                             ${PYTHON_PN}-netclient \
                             ${PYTHON_PN}-html \
                             ${PYTHON_PN}-difflib \
                             ${PYTHON_PN}-json \
                             ${PYTHON_PN}-shell \
                             ${PYTHON_PN}-sqlite3 \
                             ${PYTHON_PN}-compression \
                             ${PYTHON_PN}-xmlrpc \
                             ${PYTHON_PN}-pycryptodomex \
                             ${PYTHON_PN}-mechanize \
                             ${PYTHON_PN}-profile \
                             tzdata-africa \
                             tzdata-americas \
                             tzdata-antarctica \
                             tzdata-arctic \
                             tzdata-asia \
                             tzdata-atlantic \
                             tzdata-australia \
                             tzdata-europe \
                             tzdata-pacific \
                             kodi-addon-inputstream-adaptive-piers \
                             kodi-addon-inputstream-rtmp-piers \
                             kodi-addon-visualization-fishbmc \
                             kodi-addon-visualization-pictureit \
                             kodi-addon-visualization-matrix \
                             kodi-addon-visualization-waveform \
                             kodi-addon-visualization-shadertoy \
                             ${@'' if d.getVar('HISI_STB') == '1' else 'alsa-plugins'} \
                           "

RRECOMMENDS:${PN}:append:libc-glibc = " glibc-charmap-ibm850 \
                                        glibc-gconv-ibm850 \
                                        glibc-charmap-ibm437 \
                                        glibc-gconv-ibm437 \
                                        glibc-charmap-utf-8 \
                                        glibc-localedata-en-us \
                                      "
