SUMMARY = "Kodi Media Center"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE.GPL;md5=930e2a5f63425d8dd72dbd7391c43c46"

DEFAULT_PREFERENCE = "-1"

FILESPATH =. "${FILE_DIRNAME}/kodi-17:"

PACKAGE_ARCH = "${MACHINE}"

DEPENDS = " \
            cmake-native \
            curl-native \
            gperf-native \
            jsonschemabuilder-native \
            nasm-native \
            swig-native \
            yasm-native \
            zip-native \
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
            giflib \
            jasper \
            libass \
            libcdio \
            libcec \
            libmad \
            libmicrohttpd \
            libmms \
            libmodplug \
            libpcre \
            libplist \
            libsamplerate0 \
            libsdl-image \
            libsdl-mixer \
            libsquish \
            libssh \
            libtinyxml \
            libusb1 \
            libxslt \
            lzo \
            mpeg2dec \
            python \
            samba \
            mysql5 \
            sqlite3 \
            taglib \
            virtual/egl \
            virtual/libsdl \
            wavpack \
            yajl \
            zlib \
            ${@enable_glew(bb, d)} \
            libbluray \
            nfs-utils \
            libupnp \
            libshairport \
            libnfs \
            alsa-lib \
            alsa-plugins \
            gstreamer1.0 \
            gstreamer1.0-plugins-base \
          "

PROVIDES = "xbmc"

SRCREV = "7e52c1d94d0cbc3f8ace57b1fc74ae1582c5a869"

PV = "17.6+git${SRCPV}"
PKGV = "17.6+git${GITPKGV}"

SRC_URI = "git://github.com/xbmc/xbmc.git;branch=Krypton \
           file://0003-configure-don-t-try-to-run-stuff-to-find-tinyxml.patch \
           file://0006-Disable-DVD-support.patch \
           file://0007-Always-compile-libcpluff-as-PIC.patch \
           file://0008-kodi-config.cmake-use-CMAKE_FIND_ROOT_PATH-to-fix-cr.patch \
           file://0009-build-Add-support-for-musl-triplets.patch \
           file://0010-RssReader-Fix-compiler-warning-comparing-pointer-to-.patch \
           file://0011-Let-configure-pass-on-unknown-architectures-setting-.patch \
           file://0013-VideoPlayer-Fix-build-with-FFmpeg-3.0.patch \
           file://stb-platform.patch \
           file://stb-settings.patch \
           file://e2player.patch \
           file://add-gstplayer-support.patch \
           file://0001-fix-multilib-build.patch \
           file://visualization.patch \
           file://visualizations.zip \
"

SRC_URI_append_u5 = " file://eglwrapper.patch"
SRC_URI_append_u51 = " file://eglwrapper.patch"
SRC_URI_append_u52 = " file://eglwrapper.patch"
SRC_URI_append_u53 = " file://eglwrapper.patch"
SRC_URI_append_u5pvr = " file://eglwrapper.patch"

SRC_URI_append_AML8726 = " file://amlogic-codec-krypton-fixes.patch"
SRC_URI_append_AMLS905 = " file://amlogic-codec-krypton-fixes.patch"
SRC_URI_append_AML905D = " file://amlogic-codec-krypton-fixes.patch"

SRC_URI_append_libc-musl = " \
           file://0001-Fix-file_Emu-on-musl.patch \
           file://0002-Remove-FILEWRAP.patch \
"

inherit autotools-brokensep gettext gitpkgv pythonnative

S = "${WORKDIR}/git"

# breaks compilation
ASNEEDED = ""

ACCEL ?= ""
ACCEL_x86 = "vaapi vdpau"
ACCEL_x86-64 = "vaapi vdpau"

PACKAGECONFIG ??= "${ACCEL} mysql \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'x11', '', d)} \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'opengl', 'openglesv2', d)}"

PACKAGECONFIG[opengl] = "--enable-gl,--disable-gl,"
PACKAGECONFIG[openglesv2] = "--enable-gles,--disable-gles,virtual/egl"
PACKAGECONFIG[vaapi] = "--enable-vaapi,--disable-vaapi,libva"
PACKAGECONFIG[vdpau] = "--enable-vdpau,--disable-vdpau,libvdpau"
PACKAGECONFIG[mysql] = "--enable-mysql,--disable-mysql,mysql5"
PACKAGECONFIG[x11] = "--enable-x11,--disable-x11,libxinerama libxmu libxrandr libxtst"
PACKAGECONFIG[pulseaudio] = "--enable-pulse,--disable-pulse,pulseaudio"
PACKAGECONFIG[lcms] = "--enable-lcms2,--disable-lcms2,lcms"

EXTRA_OECONF = " \
    --disable-debug \
    --disable-libcap \
    --disable-ccache \
    --disable-mid \
    --enable-libusb \
    --enable-alsa \
    --enable-airplay \
    --disable-optical-drive \
    --enable-texturepacker=no \
    --disable-lirc \
    --disable-dbus \
"

FULL_OPTIMIZATION_armv7a = "-fexpensive-optimizations -fomit-frame-pointer -O3 -ffast-math"
FULL_OPTIMIZATION_armv7ve = "-fexpensive-optimizations -fomit-frame-pointer -O3 -ffast-math"
BUILD_OPTIMIZATION = "${FULL_OPTIMIZATION}"

EXTRA_OECONF_append = " LIBTOOL=${STAGING_BINDIR_CROSS}/${HOST_SYS}-libtool"

# for python modules
export HOST_SYS
export BUILD_SYS
export STAGING_LIBDIR
export STAGING_INCDIR
export PYTHON_DIR

def enable_glew(bb, d):
    if bb.utils.contains('PACKAGECONFIG', 'x11', True, False, d) and bb.utils.contains('DISTRO_FEATURES', 'opengl', True, False, d):
        return "glew"
    return ""

do_configure() {
    cp -a ${WORKDIR}/visualization.glspectrum ${WORKDIR}/git/addons/
    cp -a ${WORKDIR}/visualization.waveform ${WORKDIR}/git/addons/
    cp -a ${WORKDIR}/visualizations ${WORKDIR}/git/xbmc/
    cp -a ${WORKDIR}/include ${WORKDIR}/git/xbmc/addons/
    ( for i in $(find ${S} -name "configure.*" ) ; do
       cd $(dirname $i) && gnu-configize --force || true
    done )
    make -C tools/depends/target/crossguid PREFIX=${STAGING_DIR_HOST}${prefix} BASE_URL=http://ftp.vim.org/mediaplayer/xbmc/build-deps/sources

    BOOTSTRAP_STANDALONE=1 make -f bootstrap.mk JSON_BUILDER="${STAGING_BINDIR_NATIVE}/JsonSchemaBuilder"
    BOOTSTRAP_STANDALONE=1 make -f codegenerator.mk JSON_BUILDER="${STAGING_BINDIR_NATIVE}/JsonSchemaBuilder"
    oe_runconf
}

do_compile_prepend() {
    for i in $(find . -name "Makefile") ; do
        sed -i -e 's:I/usr/include:I${STAGING_INCDIR}:g' $i
    done

    for i in $(find . -name "*.mak*" -o    -name "Makefile") ; do
        sed -i -e 's:I/usr/include:I${STAGING_INCDIR}:g' -e 's:-rpath \$(libdir):-rpath ${libdir}:g' $i
    done
}

INSANE_SKIP_${PN} = "rpaths"

FILES_${PN} = "${libdir}/kodi ${libdir}/xbmc"
FILES_${PN} += "${bindir}/kodi ${bindir}/xbmc"
FILES_${PN} += "${datadir}/icons ${datadir}/kodi ${datadir}/xbmc"
FILES_${PN} += "${bindir}/kodi-standalone ${bindir}/xbmc-standalone ${datadir}/xsessions"
FILES_${PN}-dev = "${includedir}"
FILES_${PN}-dbg += "${libdir}/kodi/.debug ${libdir}/kodi/*/.debug ${libdir}/kodi/*/*/.debug ${libdir}/kodi/*/*/*/.debug ${datadir}/applications"

# kodi uses some kind of dlopen() method for libcec so we need to add it manually
# OpenGL builds need glxinfo, that's in mesa-demos
RRECOMMENDS_${PN}_append = " \
   libcec \
   python \
   python-ctypes \
   python-lang \
   python-re \
   python-netclient \
   python-html \
   python-difflib \
   python-json \
   python-zlib \
   python-shell \
   python-sqlite3 \
   python-compression \
   python-profile \
   python-mmap \
   libcurl \
   lsb \
   os-release \
   ${@bb.utils.contains('PACKAGECONFIG', 'x11', 'xrandr xdpyinfo', '', d)} \
   \
    python-distutils \
    python-subprocess \
    python-robotparser \
    python-mechanize \
    python-threading \
    python-xml \
    python-netserver \
    python-misc \
    python-pygobject \
    python-pygobject-lib \
    python-textutils \
    python-simplejson \
    python-xmlrpc   \
    python-pprint \
    python-email \
    python-compile \
    python-compiler \
    python-numbers \
    python-pkgutil \
    python-pycurl \
    python-async \
    python-docutils \
    python-iniparse \
    python-hotshot \
    python-importlib \
    python-curses \
    python-pycrypto \
    python-db \
    python-argparse \
    python-doctest \
    python-plistlib\
    python-mailbox \
    python-terminal \
    python-smtpd \
    python-pydoc \
    python-syslog \
    python-resource \
"
RRECOMMENDS_${PN}_append_libc-glibc = " \
    glibc-charmap-ibm850 \
    glibc-gconv-ibm850 \
    glibc-gconv-unicode \
    glibc-gconv-utf-32 \
    glibc-charmap-utf-8 \
    glibc-localedata-en-us \
"

RPROVIDES_${PN} += "xbmc"

TOOLCHAIN = "gcc"
