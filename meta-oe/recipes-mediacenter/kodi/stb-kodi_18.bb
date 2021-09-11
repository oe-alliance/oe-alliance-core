SUMMARY = "Kodi Media Center"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=7b423f1c9388eae123332e372451a4f7"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-18:"

PACKAGE_ARCH = "${MACHINE}"

inherit cmake gettext python-dir pythonnative

DEPENDS += " \
            fmt \
            flatbuffers flatbuffers-native \
            fstrcmp \
            rapidjson \
            crossguid \
            libdvdnav libdvdcss libdvdread \
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
            boost \
            bzip2 \
            curl \
            libdcadec \
            enca \
            expat \
            faad2 \
            ffmpeg \
            fontconfig \
            fribidi \
            glib-2.0 \ 
            giflib \
            libass \
            libcdio \
            libcec \
            libinput \
            libbluray \
            libmad \
            libmicrohttpd \
            libmms \
            libmodplug \
            libnfs \
            libpcre \
            libplist \
            libsamplerate0 \
            libsquish \
            libssh \
            libtinyxml \
            libusb1 \
            libxkbcommon \
            libxslt \
            lzo \
            mpeg2dec \
            python \
            samba \
            sqlite3 \
            taglib \
            virtual/egl \
            wavpack \
            yajl \
            zlib \
            texturepacker-native \
            \
            gstreamer1.0 \
            gstreamer1.0-plugins-base \
          "

#SRCREV = "ded117bbd636fc88802599a590e26b1d0fd7c609" #18.3
#SRCREV = "61d162be4091bf7154214c8422abd0fdf6cedc45" #18.4
#SRCREV = "103415d16038a57f59842cac72d4929389f224a9" #18.5
#SRCREV = "8e967df9218279618bcbfa8a898d8f80f7b4e449" #18.6
#SRCREV = "0442d7060875ff5da7d028dea918054b28d6c80a" #18.7
#SRCREV = "45686bddb1f308ec580f97eb4b228b8a6606b320" #18.8
SRCREV ="0655c2c71821567e4c21c1c5a508a39ab72f0ef1"

# 'patch' doesn't support binary diffs
PATCHTOOL = "git"

PR = "r15"

PV = "18.9-gitr${SRCPV}"
SRC_URI = "git://github.com/xbmc/xbmc.git;protocol=https;branch=Leia \
           \
           file://0001-Add-support-for-musl-triplets.patch \
           file://0004-Replace-u_int64_t-with-uint64_t-from-stdint.h.patch \
           \
           file://0005-estuary-move-recently-added-entries-to-the-top-in-ho.patch \
           file://0006-kodi.sh-set-mesa-debug.patch \
           file://0007-peripheral-settings-export-CEC-device_name-in-GUI.patch \
           file://0010-flatbuffers.patch \
           file://0011-WIP-windowing-gbm-add-option-to-limit-gui-size.patch \
           \
           file://PR15286-shader-nopow.patch \
           file://15941.patch \
           \
           file://stb-support.patch \
           file://stb-settings.patch \
           file://e2player.patch \
           file://0001-introduce-basic-GstPlayer.patch \
           file://0001-add-find-GLIB.patch \
          "

SRC_URI_append_u5 = " file://eglwrapper.patch"
SRC_URI_append_u51 = " file://eglwrapper.patch"
SRC_URI_append_u52 = " file://eglwrapper.patch"
SRC_URI_append_u53 = " file://eglwrapper.patch"
SRC_URI_append_u54 = " file://eglwrapper.patch"
SRC_URI_append_u55 = " file://eglwrapper.patch"
SRC_URI_append_u56 = " file://eglwrapper.patch"
SRC_URI_append_u57 = " file://eglwrapper.patch"
SRC_URI_append_u571 = " file://eglwrapper.patch"
SRC_URI_append_u5pvr = " file://eglwrapper.patch"
SRC_URI_append_u532 = " file://eglwrapper.patch"
SRC_URI_append_u533 = " file://eglwrapper.patch"


SRC_URI_append_libc-musl = " \
           file://0002-Fix-file_Emu-on-musl.patch \
           file://0003-Remove-FILEWRAP.patch \
"

S = "${WORKDIR}/git"

# breaks compilation
CCACHE_DISABLE = "1"
ASNEEDED = ""

ACCEL ?= ""
ACCEL_x86 = "vaapi vdpau"
ACCEL_x86-64 = "vaapi vdpau"

# Default to GBM everywhere, sucks to be nvidia
WINDOWSYSTEM ?= "stb"

PACKAGECONFIG ??= "${ACCEL} ${WINDOWSYSTEM} pulseaudio lcms lto ${@bb.utils.contains("TARGET_ARCH", "arm", "gold", "", d)} \
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

PACKAGECONFIG[gold] = "-DENABLE_LDGOLD=ON,-DENABLE_LDGOLD=OFF"
PACKAGECONFIG[lto] = "-DUSE_LTO=${@oe.utils.cpu_count()},-DUSE_LTO=OFF"

LDFLAGS += "${TOOLCHAIN_OPTIONS}"
LDFLAGS_append_mips = " -latomic"
LDFLAGS_append_mipsel = " -latomic"
LDFLAGS_append_mips64 = " -latomic"
LDFLAGS_append_mips64el = " -latomic"

KODI_ARCH = ""
KODI_ARCH_mips = "-DWITH_ARCH=${TARGET_ARCH}"
KODI_ARCH_mipsel = "-DWITH_ARCH=${TARGET_ARCH}"
KODI_ARCH_mips64 = "-DWITH_ARCH=${TARGET_ARCH}"
KODI_ARCH_mips64el = "-DWITH_ARCH=${TARGET_ARCH}"

KODI_DISABLE_INTERNAL_LIBRARIES = " \
  -DENABLE_INTERNAL_CROSSGUID=OFF \
  -DENABLE_INTERNAL_FLATBUFFERS=OFF \
  -DENABLE_INTERNAL_FMT=OFF \
  -DENABLE_INTERNAL_FSTRCMP=0 \
  -DENABLE_INTERNAL_RapidJSON=OFF \
  -DENABLE_INTERNAL_FFMPEG=OFF \
"

EXTRA_OECMAKE = " \
    ${KODI_ARCH} \
    ${KODI_DISABLE_INTERNAL_LIBRARIES} \
    \
    -DNATIVEPREFIX=${STAGING_DIR_NATIVE}${prefix} \
    -DJava_JAVA_EXECUTABLE=/usr/bin/java \
    -DWITH_TEXTUREPACKER=${STAGING_BINDIR_NATIVE}/TexturePacker \
    -DWITH_JSONSCHEMABUILDER=${STAGING_BINDIR_NATIVE}/JsonSchemaBuilder \
    \
    -DCMAKE_NM='${NM}' \
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

FULL_OPTIMIZATION_armv7a = "-fexpensive-optimizations -fomit-frame-pointer -O3 -ffast-math"
FULL_OPTIMIZATION_armv7ve = "-fexpensive-optimizations -fomit-frame-pointer -O3 -ffast-math"
BUILD_OPTIMIZATION = "${FULL_OPTIMIZATION}"

# for python modules
export HOST_SYS
export BUILD_SYS
export STAGING_LIBDIR
export STAGING_INCDIR
export PYTHON_DIR

export TARGET_PREFIX

do_configure_prepend() {
    # Ensure 'nm' can find the lto plugins 
    liblto=$(find ${STAGING_DIR_NATIVE} -name "liblto_plugin.so.0.0.0")
    mkdir -p ${STAGING_LIBDIR_NATIVE}/bfd-plugins
    ln -sf $liblto ${STAGING_LIBDIR_NATIVE}/bfd-plugins/liblto_plugin.so

    sed -i -e 's:CMAKE_NM}:}${TARGET_PREFIX}gcc-nm:' ${S}/xbmc/cores/DllLoader/exports/CMakeLists.txt
}

INSANE_SKIP_${PN} = "rpaths already-stripped"

FILES_${PN} = "${libdir}/kodi ${libdir}/xbmc"
FILES_${PN} += "${bindir}/kodi ${bindir}/xbmc"
FILES_${PN} += "${datadir}/icons ${datadir}/kodi ${datadir}/xbmc"
FILES_${PN} += "${bindir}/kodi-standalone ${bindir}/xbmc-standalone ${datadir}/xsessions"
FILES_${PN} += "${libdir}/firewalld"
FILES_${PN}-dev = "${includedir}"
FILES_${PN}-dbg += "${libdir}/kodi/.debug ${libdir}/kodi/*/.debug ${libdir}/kodi/*/*/.debug ${libdir}/kodi/*/*/*/.debug"

# kodi uses some kind of dlopen() method for libcec so we need to add it manually
# OpenGL builds need glxinfo, that's in mesa-demos
RRECOMMENDS_${PN}_append = " libcec \
                             libcurl \
                             libnfs \
                             nss \
                             os-release \
                             ${@bb.utils.contains('PACKAGECONFIG', 'x11', 'xdyinfo xrandr xinit mesa-demos', '', d)} \
                             python \
                             python-ctypes \
                             python-lang \
                             python-re \
                             python-netclient \
                             python-html \
                             python-difflib \
                             python-pycryptodome \
                             python-json \
                             python-zlib \
                             python-shell \
                             python-sqlite3 \
                             python-compression \
                             python-xmlrpc \
                             python-mechanize \
                             python-profile \
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
                             kodi-addon-inputstream-adaptive \
                             kodi-addon-inputstream-rtmp \
                             alsa-plugins \
                           "

RRECOMMENDS_${PN}_append_libc-glibc = " glibc-charmap-ibm850 \
                                        glibc-gconv-ibm850 \
                                        glibc-charmap-ibm437 \
                                        glibc-gconv-ibm437 \
                                        glibc-gconv-unicode \
                                        glibc-gconv-utf-32 \
                                        glibc-charmap-utf-8 \
                                        glibc-localedata-en-us \
                                      "
