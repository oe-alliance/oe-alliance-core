SUMMARY = "XBMC Media Center"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE.GPL;md5=930e2a5f63425d8dd72dbd7391c43c46"

PROVIDES += "virtual/xbmc"
RPROVIDES_${PN} += "virtual/xbmc"

DEPENDS = "libamcodec opengl-amlogic libssh libxslt ffmpeg-kodi libusb1 libcec libplist expat yajl gperf-native fribidi mpeg2dec samba fontconfig curl python libass libmodplug libmicrohttpd wavpack libmms cmake-native libsdl-image libsdl-mixer mysql5 sqlite3 libmms faad2 libcdio libpcre boost lzo enca avahi libsamplerate0 bzip2 virtual/libsdl jasper zip-native zlib libtinyxml taglib libbluray libshairport librtmp zlib libnfs libxslt shairplay libsquish virtual/egl jsonschemabuilder-native libdcadec libcrossguid"

inherit autotools-brokensep autotools lib_package pkgconfig gettext python-dir

PV = "16.1"
PR = "r1"

SRC_URI = "https://openspa.webhop.info/drivers/kodi/WetekPlay-Jarvis-Final.tar.gz \
    file://keyboard.xml \
    file://remote.xml \
    file://9703.patch \
"

SRC_URI[md5sum] = "6ebfc374bdd63b9c8966eb84711f9e94"
SRC_URI[sha256sum] = "996334d6419a5ceac6e22ad440ef661e9d7c3f75686172fdca13779ed027e035"

S = "${WORKDIR}/Jarvis/"

# breaks compilation
CCACHE = ""
CACHED_CONFIGUREVARS += " \
    ac_cv_path_PYTHON="${STAGING_BINDIR_NATIVE}/python-native/python" \
"

FULL_OPTIMIZATION_armv7a = "-fexpensive-optimizations -fomit-frame-pointer -O4 -ffast-math"
BUILD_OPTIMIZATION = "${FULL_OPTIMIZATION}"

CXXFLAGS += " -I${STAGING_KERNEL_DIR}/include/uapi -I${STAGING_KERNEL_DIR}/include -I${STAGING_INCDIR} -I${STAGING_INCDIR}/amlogic "
CFLAGS += " -I${STAGING_KERNEL_DIR}/include/uapi -I${STAGING_KERNEL_DIR}/include -I${STAGING_INCDIR} -I${STAGING_INCDIR}/amlogic "

EXTRA_OECONF = " \
    --build=${BUILD_SYS} \
    --host=${HOST_SYS} \
    --target=${TARGET_SYS} ${@append_libtool_sysroot(d)} \
    --disable-rpath \
    --enable-codec=amcodec \
    --enable-gles \
    --enable-libusb \
    --enable-airplay \
    --disable-optical-drive \
    --enable-external-libraries \
    --with-ffmpeg=shared \
    --disable-x11 \
    --disable-sdl \
    --disable-joystick \
    --disable-afpclient \
    --disable-mdnsembedded \
    --disable-tiff \
    --disable-xrandr \
    --disable-gl \
    --disable-vdpau \
    --disable-vaapi \
    --disable-openmax \
    --enable-udev \
    --disable-texturepacker \
    --enable-airtunes \
    --enable-optimizations \
    --enable-avahi \
    --disable-mid \
    --enable-rsxs \
    --disable-libcec \
    --enable-pulse=no \
"

EXTRA_OECONF_append_armv7a = "--cpu=cortex-a9"

# for python modules
export HOST_SYS
export BUILD_SYS
export STAGING_LIBDIR
export STAGING_INCDIR
export PYTHON_DIR

do_configure() {
    cp ${WORKDIR}/keyboard.xml ${S}/system/keymaps
    cp ${WORKDIR}/remote.xml ${S}/system/keymaps
    export PYTHON_VERSION=2.7
    export PYTHON_CPPFLAGS=-I${STAGING_DIR_HOST}/usr/include/python2.7
    export PYTHON_LDFLAGS="-L${STAGING_DIR_HOST}/usr/lib/python2.7 -lpython2.7"
    export PYTHON_SITE_PKG=${STAGING_DIR_HOST}/usr/lib/python2.7/site-packages
    export PYTHON_EXTRA_LIBS="-lpthread -ldl -lutil"
    export PYTHON_EXTRA_LDFLAGS="-Xlinker -export-dynamic"
    cd ${S}
    sed -i 's%PYTHON_VERSION=$ac_python_version%PYTHON_VERSION=2.7%' configure.ac

    ./bootstrap
    oe_runconf
}


do_compile_prepend() {
    for i in $(find . -name "Makefile") ; do
        sed -i -e 's:I/usr/include:I${STAGING_INCDIR}:g' $i
    done

    for i in $(find . -name "*.mak*" -o -name "Makefile") ; do
        sed -i -e 's:I/usr/include:I${STAGING_INCDIR}:g' -e 's:-rpath \$(libdir):-rpath ${libdir}:g' $i
    done
}

INSANE_SKIP_${PN} = "rpaths"

do_install_prepend(){
    cd ${S}
}

do_compile_prepend(){
    cd ${S}
}

do_install_append(){
    find ${D} -name "*.cmake" -exec rm -rf {} \;
    rm -rf ${D}/usr/share/kodi/addons/service.xbmc.versioncheck
}

do_package_qa(){
}

PARALLEL_MAKE = ""

# xbmc uses some kind of dlopen() method for libcec so we need to add it manually
# OpenGL builds need glxinfo, that's in mesa-demos
RRECOMMENDS_${PN}_append = " libcec \
                             python \
                             python-lang \
                             python-re \
                             python-netclient \
                             libcurl \
                             opengl-amlogic \
                             python-sqlite3 \
                             python-modules \
"
RRECOMMENDS_${PN}_append_libc-glibc = " glibc-charmap-ibm850 glibc-gconv-ibm850 glibc-gconv-cp1252 glibc-gconv-utf-32 glibc-charmap-utf-8"

FILES_${PN} += "${datadir}/xsessions ${datadir}/icons /usr/bin /usr/share /usr/lib"
FILES_${PN}-dbg += "\
    /usr/lib/kodi/.debug \
    /usr/lib/kodi/system/.debug \
    /usr/lib/kodi/system/*/*/.debug \
    /usr/lib/kodi/addons/*/.debug \
    "

COMPATIBLE_MACHINE = "(wetekplay)"
