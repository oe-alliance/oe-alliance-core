SUMMARY = "Kodi Media Center"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=7b423f1c9388eae123332e372451a4f7"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}-20:"

PACKAGE_ARCH = "${MACHINE}"

inherit ccache cmake gettext pkgconfig ${PYTHON_PN}native

DEPENDS += " \
            fmt \
            flatbuffers flatbuffers-native \
            fstrcmp \
            rapidjson \
            crossguid \
            libdvdnav libdvdcss libdvdread \
            kodi-ffmpeg \
            autoconf-native \
            automake-native \
            git-native \
            curl-native \
            gperf-native \
            jsonschemabuilder-native \
            nasm-native \
            swig-native \
            unzip-native \
            yasm-native \
            zip-native \
            \
            avahi \
            bzip2 \
            curl \
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
            libxkbcommon \
            libxml2 \
            libxslt \
            lzo \
            mpeg2dec \
            ${PYTHON_PN} \
            samba \
            sqlite3 \
            taglib \
            virtual/egl \
            wavpack \
            zlib \
            texturepacker-native \
            \
            gstreamer1.0 \
            gstreamer1.0-plugins-base \
          "
inherit gitpkgv
# 20.0 Nexus
SRCREV = "${AUTOREV}"

# 'patch' doesn't support binary diffs
PATCHTOOL = "git"

PR = "r0"

PV = "20.0+gitr${SRCPV}"

SRC_URI = "git://github.com/xbmc/xbmc.git;protocol=https;branch=master \
           file://0001-flatbuffers-20.patch \
           file://0002-readd-Touchscreen-settings.patch \
           file://0003-shader-nopow-20.patch \
           file://0004-stb-settings-20.patch \
           file://0005-stb-support-20.patch \
           file://0006-add-winsystemfactory-windowing-init.patch \
           file://0007-adapt-window-system-registration.patch \
           file://0008-reinstate-system-h.patch \
           file://0009-reinstate-platform-defines.patch \
           file://0010-AEELDParser.cpp-fix-rtrim-function-for-clang.patch \
           file://0011-FindLibDvd.cmake-build-with-external-source.patch \
           ${@bb.utils.contains_any('MACHINE_FEATURES', 'hisil-3798mv200 hisil-3798mv310 hisi hisil', '' , 'file://0100-e2-player.patch', d)} \
           ${@bb.utils.contains_any('MACHINE_FEATURES', 'hisil-3798mv200 hisil-3798mv310 hisi hisil', '' , 'file://0101-gst-player.patch', d)} \
          "

S = "${WORKDIR}/git"

ACCEL ?= ""
ACCEL:x86 = "vaapi vdpau"
ACCEL:x86-64 = "vaapi vdpau"

# Default to GBM everywhere, sucks to be nvidia
WINDOWSYSTEM ?= "stb"

#[cmake] only use APP_RENDER_SYSTEM
#https://github.com/xbmc/xbmc/commit/d159837cf736c9ba17772ba52e4ce95aa3625528
APPRENDERSYSTEM ?= "gles"

TOOLCHAIN:arm ?= "clang"

PACKAGECONFIG ?= "${ACCEL} ${WINDOWSYSTEM} pulseaudio lcms lto \
                   ${@bb.utils.contains('TOOLCHAIN', 'clang', 'clang', '', d)} \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-lld', 'lld', '', d)} \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'x11', '', d)} \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'opengl', 'openglesv2', d)}"

# Core windowing system choices

PACKAGECONFIG[x11] = "-DCORE_PLATFORM_NAME=x11,,libxinerama libxmu libxrandr libxtst glew"
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
PACKAGECONFIG[lcms] = ",,lcms"

# Compilation tunes

PACKAGECONFIG[lld] = "-DENABLE_LLD=ON,-DENABLE_LLD=OFF"
PACKAGECONFIG[clang] = "-DENABLE_CLANGFORMAT=ON -DENABLE_CLANGTIDY=ON,-DENABLE_CLANGFORMAT=OFF -DENABLE_CLANGTIDY=OFF"
PACKAGECONFIG[gold] = "-DENABLE_LDGOLD=ON,-DENABLE_LDGOLD=OFF"
PACKAGECONFIG[lto] = "-DUSE_LTO=${@oe.utils.cpu_count()},-DUSE_LTO=OFF"

LDFLAGS += "${TOOLCHAIN_OPTIONS}"
LDFLAGS:append:mipsarch = " -latomic"
EXTRA_OECMAKE:append:mipsarch = " -DWITH_ARCH=${TARGET_ARCH}"

KODI_DISABLE_INTERNAL_LIBRARIES = " \
  -DENABLE_INTERNAL_CROSSGUID=OFF \
  -DENABLE_INTERNAL_FLATBUFFERS=OFF \
  -DENABLE_INTERNAL_FMT=OFF \
  -DENABLE_INTERNAL_FSTRCMP=0 \
  -DENABLE_INTERNAL_RapidJSON=OFF \
  -DENABLE_INTERNAL_FFMPEG=OFF \
"

# Allow downloads during internals build
do_compile[network] = "1"

RUNTIME:arm ?= "llvm"

RUNTIME_NM = "${@bb.utils.contains('RUNTIME', 'llvm', '${TARGET_PREFIX}llvm-nm', '${TARGET_PREFIX}gcc-nm', d)}"

EXTRA_OECMAKE = " \
    ${KODI_ARCH} \
    ${KODI_DISABLE_INTERNAL_LIBRARIES} \
    -DAPP_RENDER_SYSTEM=${APPRENDERSYSTEM} \
    \
    -DNATIVEPREFIX=${STAGING_DIR_NATIVE}${prefix} \
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
    -DFFMPEG_PATH=${STAGING_DIR_TARGET} \
    -DLIBDVD_INCLUDE_DIRS=${STAGING_INCDIR} \
    -DNFS_INCLUDE_DIR=${STAGING_INCDIR} \
    -DSHAIRPLAY_INCLUDE_DIR=${STAGING_INCDIR} \
    \
    -DENABLE_AIRTUNES=ON \
    -DENABLE_OPTICAL=OFF \
    -DENABLE_DVDCSS=OFF \
    -DENABLE_DEBUGFISSION=OFF \
    -DCMAKE_BUILD_TYPE=RelWithDebInfo \
"

# OECMAKE_GENERATOR="Unix Makefiles"
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
    # Ensure 'nm' can find the lto plugins 
    liblto=$(find ${STAGING_DIR_NATIVE} -name "liblto_plugin.so.0.0.0")
    mkdir -p ${STAGING_LIBDIR_NATIVE}/bfd-plugins
    ln -sf $liblto ${STAGING_LIBDIR_NATIVE}/bfd-plugins/liblto_plugin.so

#    sed -i -e 's:CMAKE_NM}:}${TARGET_PREFIX}gcc-nm:' ${S}/xbmc/cores/DllLoader/exports/CMakeLists.txt
}

INSANE_SKIP:${PN} = "rpaths already-stripped textrel"

FILES:${PN} = "${libdir}/kodi ${libdir}/xbmc"
FILES:${PN} += "${bindir}/kodi ${bindir}/xbmc ${bindir}/kodi-TexturePacker"
FILES:${PN} += "${datadir}/icons ${datadir}/kodi ${datadir}/xbmc ${datadir}/applications"
FILES:${PN} += "${bindir}/kodi-standalone ${bindir}/xbmc-standalone ${datadir}/xsessions ${datadir}/metainfo"
FILES:${PN} += "${libdir}/firewalld"
FILES:${PN}-dev = "${includedir}"
FILES:${PN}-dbg += "${libdir}/kodi/.debug ${libdir}/kodi/*/.debug ${libdir}/kodi/*/*/.debug ${libdir}/kodi/*/*/*/.debug"

# kodi uses some kind of dlopen() method for libcec so we need to add it manually
# OpenGL builds need glxinfo, that's in mesa-demos
RRECOMMENDS:${PN}:append = " libcec \
                             libcurl \
                             libnfs \
                             nss \
                             os-release \
                             ${@bb.utils.contains('PACKAGECONFIG', 'x11', 'xrandr xinit mesa-demos', '', d)} \
                             ${PYTHON_PN} \
                             ${PYTHON_PN}-ctypes \
                             ${@bb.utils.contains("PYTHON_PN", "python", "${PYTHON_PN}-lang", "", d)} \
                             ${@bb.utils.contains("PYTHON_PN", "python", "${PYTHON_PN}-re", "", d)} \
                             ${PYTHON_PN}-netclient \
                             ${PYTHON_PN}-html \
                             ${PYTHON_PN}-difflib \
                             ${PYTHON_PN}-json \
                             ${@bb.utils.contains("PYTHON_PN", "python", "${PYTHON_PN}-zlib", "", d)} \
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
                             xkeyboard-config \
                             kodi-addon-inputstream-adaptive-nexus \
                             kodi-addon-inputstream-rtmp-nexus \
                             alsa-plugins \
                           "

RRECOMMENDS:${PN}:append:libc-glibc = " glibc-charmap-ibm850 \
                                        glibc-gconv-ibm850 \
                                        glibc-charmap-ibm437 \
                                        glibc-gconv-ibm437 \
                                        glibc-gconv-unicode \
                                        glibc-gconv-utf-32 \
                                        glibc-charmap-utf-8 \
                                        glibc-localedata-en-us \
                                      "
