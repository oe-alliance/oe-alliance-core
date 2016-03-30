SUMMARY = "Kodi Media Center"

PACKAGE_ARCH := "${MACHINE_ARCH}"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE.GPL;md5=930e2a5f63425d8dd72dbd7391c43c46"

DEPENDS = "vuplus-libgles-${MACHINE} libxslt libusb1 libcec libplist expat yajl gperf-native fribidi mpeg2dec samba fontconfig curl python libass libmodplug libmicrohttpd wavpack libmms cmake-native libsdl-image mysql5 sqlite3 libmms faad2 libcdio libpcre boost lzo enca avahi libsamplerate0  bzip2 virtual/libsdl jasper zip-native zlib libtinyxml libtinyxml-native taglib libbluray libshairport librtmp zlib libnfs libxslt libsquish jsonschemabuilder-native ffmpeg-kodi libdcadec crossguid"

PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"

RDEPENDS_${PN} = "python"
RDEPENDS_${PN} += "\
    python-distutils \
    python-subprocess \
    python-robotparser \
    python-mechanize \
    python-threading \
    python-shell \
    python-zlib \
    python-sqlite3 \
    python-json \
    python-xml \
    python-html \
    python-netserver \
    python-misc \
    python-pygobject \
    python-pygobject-lib \
    python-textutils \
    python-simplejson \
    python-xmlrpc   \
    python-pprint \
    python-difflib \
    python-email \
    python-compression \
    python-compile \
    python-compiler \
    python-numbers \
    nfs-utils-client \
    libshairport \
    glibc-gconv-utf-32 \
    xz \
    tiff \
    yajl \
    libxslt \
    libupnp \
    libplist \
    librtmp \
    libbluray \
    libnfs \
    alsa-lib \
    alsa-plugins \
"

inherit autotools-brokensep gettext python-dir

inherit gitpkgv
SRCREV = "43af1c2539e787671554d4460ff65711420c6cd4"
PV = "16.0"
PR = "r0-${SRCREV}"

SRC_URI = "git://github.com/OpenSPA/xbmc.git;protocol=git;branch=vu_kodi_16.x \
    http://archive.vuplus.com/download/build_support/xbmc-support_${GLPR}${GLARCH}.tar.gz;name=xbmc-support \
"

S = "${WORKDIR}/git"

CACHED_CONFIGUREVARS += " \
 ac_cv_path_PYTHON="${STAGING_BINDIR_NATIVE}/python-native/python" \
"

EXTRA_OECONF = " \
    --enable-gles \
    --enable-libusb \
    --enable-airplay \
    --enable-rtmp \
    --enable-alsa \
    --disable-pulse \
    --disable-optical-drive \
    --enable-external-libraries \
    --disable-ssh \
    --disable-x11 \
    --disable-joystick \
    --disable-libcec \
    --disable-lirc \
    --disable-texturepacker \
    --with-ffmpeg=shared \
    --enable-pulse=no \
"

#EXTRA_OECONF_append_armv7a = " --cpu=cortex-a8 "

FULL_OPTIMIZATION_armv7a = "-fexpensive-optimizations -fomit-frame-pointer -O4 -ffast-math"
BUILD_OPTIMIZATION = "${FULL_OPTIMIZATION}"

# for python modules
export HOST_SYS
export BUILD_SYS
export STAGING_LIBDIR
export STAGING_INCDIR
export PYTHON_DIR

export TINYXML_CFLAGS="-I/${STAGING_INCDIR}"
export TINYXML_LIBS="-L${STAGING_LIBDIR} -ltinyxml"
export SQUISH_CFLAGS="-I/${STAGING_INCDIR}"  
export SQUISH_LIBS="-L${STAGING_LIBDIR} -lsquish"

do_configure() {
    cp -av ${WORKDIR}/xbmc-support/gles_init.* ${WORKDIR}/git/xbmc/windowing/egl/
    ./bootstrap
    oe_runconf
}

#PARALLEL_MAKE = " -j8 "

do_compile_prepend() {
    for i in $(find . -name "Makefile") ; do
        sed -i -e 's:I/usr/include:I${STAGING_INCDIR}:g' $i
    done

    for i in $(find . -name "*.mak*" -o	-name "Makefile") ; do
        sed -i -e 's:I/usr/include:I${STAGING_INCDIR}:g' -e 's:-rpath \$(libdir):-rpath ${libdir}:g' $i
    done
}

INSANE_SKIP_${PN} = "rpaths"

do_install_append(){
    find ${D} -name "*.cmake" -exec rm -rf {} \;
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/xbmc-support/xbmc.helper ${D}${bindir}
    rm -rf ${D}/usr/share/kodi/addons/service.xbmc.versioncheck
}

do_package_qa(){
}

# xbmc uses some kind of dlopen() method for libcec so we need to add it manually
RRECOMMENDS_${PN}_append = " libcec \
                             python \
                             python-lang \
                             python-re \
                             python-netclient \
                             libcurl \
                             "
RRECOMMENDS_${PN}_append_libc-glibc = " glibc-charmap-ibm850 glibc-gconv-ibm850"

INSANE_SKIP_${PN} = "already-stripped"

PACKAGE_ARCH := "${MACHINE_ARCH}"

FILES_${PN} += "${datadir}/xsessions ${datadir}/icons /usr/bin /usr/share /usr/lib"
FILES_${PN}-dbg += "\
    /usr/lib/kodi/.debug \
    /usr/lib/kodi/system/.debug \
    /usr/lib/kodi/system/*/*/.debug \
    /usr/lib/kodi/addons/*/.debug \
    "
