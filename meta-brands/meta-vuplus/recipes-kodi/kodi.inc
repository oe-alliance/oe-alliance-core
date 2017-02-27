SUMMARY = "Kodi Media Center"

PACKAGE_ARCH := "${MACHINE_ARCH}"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE.GPL;md5=930e2a5f63425d8dd72dbd7391c43c46"

PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"

DEPENDS = "\
	vuplus-libgles-${MACHINE} \
	libxslt \
	libusb1 \
	libcec \
	libplist \
	expat \
	yajl \
	gperf-native \
	fribidi \
	mpeg2dec \
	samba \
	fontconfig \
	curl \
	python \
	libass \
	libmodplug \
	libmicrohttpd \
	wavpack \
	libmms\
	cmake-native \
	libsdl-image \
	libsdl-mixer \
	mysql5 \
	sqlite3 \
	libmms \
	faad2 \
	libcdio \
	libpcre \
	boost \
	lzo \
	enca \
	avahi \
	libsamplerate0 \
	bzip2 \
	virtual/libsdl \
	jasper \
	zip-native \
	zlib \
	libtinyxml \
	taglib \
	libbluray \
	libshairport \
	librtmp \
	zlib \
	libnfs \
	libxslt \
	libsquish \
	libdcadec \
	libcrossguid \
"

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
	python-pkgutil \
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
	libtinyxml \
	alsa-lib \
	alsa-plugins \
	shairplay \
"

inherit autotools-brokensep autotools lib_package pkgconfig gettext python-dir

SRCDATE = "20170203"
PV = "17.0"
PR = "r0-${SRCDATE}"

SRC_URI = "https://openspa.webhop.info/drivers/kodi/Vuplus-Krypton-Final-${SRCDATE}.tar.gz \
    http://archive.vuplus.com/download/build_support/kodi/xbmc-support_${MACHINE}_${GLPR}.tar.gz;name=xbmc-support \
"

SRC_URI[md5sum] = "355783a7e4aa8bed63e57efacecbeb03"
SRC_URI[sha256sum] = "4640553d1b3f9d7fa1192a74a233e0010031c2d68029b97d2059c3fc2dfa3ce4"

S = "${WORKDIR}/Krypton"

CACHED_CONFIGUREVARS += " \
 ac_cv_path_PYTHON="${STAGING_BINDIR_NATIVE}/python-native/python" \
"

EXTRA_OECONF = " \
	--enable-gles \
	--enable-libusb \
	--enable-airplay \
	--enable-rtmp \
	--enable-optimizations \
	--disable-optical-drive \
	--enable-external-libraries \
	--disable-ssh \
	--disable-x11 \
	--disable-dbus \
	--disable-pulse \
	--disable-gtest \
	--disable-joystick \
	--enable-alsa \
	--disable-pulse \
	--disable-lirc \
	--disable-libcec \
	--disable-texturepacker \
	--disable-debug \
	--enable-pulse=no \
"

FULL_OPTIMIZATION_armv7a = "-fexpensive-optimizations -fomit-frame-pointer -O4 -ffast-math"
BUILD_OPTIMIZATION = "${FULL_OPTIMIZATION}"

# for python modules
export HOST_SYS
export BUILD_SYS
export STAGING_LIBDIR
export STAGING_INCDIR
export PYTHON_DIR

do_configure() {
    cp -av ${WORKDIR}/xbmc-support/gles_init.* ${WORKDIR}/Krypton/xbmc/windowing/egl/
    sh bootstrap
    sed -i 's/-V -qversion//' configure
    oe_runconf
}

do_compile_prepend() {
    for i in $(find . -name "Makefile") ; do
        sed -i -e 's:I/usr/include:I${STAGING_INCDIR}:g' $i
    done

    for i in $(find . -name "*.mak*" -o	-name "Makefile") ; do
        sed -i -e 's:I/usr/include:I${STAGING_INCDIR}:g' -e 's:-rpath \$(libdir):-rpath ${libdir}:g' $i
    done
}

INSANE_SKIP_${PN} = "rpaths"

# on ARM architectures xbmc will use GLES which will make the regular wrapper fail, so start it directly
#do_install_append_arm() {
#	sed -i -e 's:Exec=xbmc:Exec=${libdir}/xbmc/xbmc.bin:g' ${D}${datadir}/applications/xbmc.desktop
#}

do_install_append(){
    find ${D} -name "*.cmake" -exec rm -rf {} \;
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/xbmc-support/xbmc.helper ${D}${bindir}
    install -m 0755 ${WORKDIR}/xbmc-support/libxbmc_base.so ${D}${libdir}
}

do_package_qa(){
}

FILES_${PN} = "${libdir}/kodi ${libdir}/xbmc"
FILES_${PN} += "${bindir}/kodi ${bindir}/xbmc ${bindir}/xbmc.helper"
FILES_${PN} += "${datadir}/icons ${datadir}/kodi ${datadir}/xbmc"
FILES_${PN} += "${libdir}/libxbmc_base.so"
FILES_${PN}-bin = "${bindir}/kodi-standalone ${bindir}/xbmc-standalone ${datadir}/xsessions"
FILES_${PN}-dev = "${includedir}"
FILES_${PN}-dbg += "${libdir}/kodi/.debug ${libdir}/kodi/*/.debug ${libdir}/kodi/*/*/.debug ${libdir}/kodi/*/*/*/.debug ${datadir}/applications"

# xbmc uses some kind of dlopen() method for libcec so we need to add it manually
RRECOMMENDS_${PN}_append = " libcec \
                             python \
                             python-lang \
                             python-re \
                             python-netclient \
                             libcurl \
                             lsb \
                             os-release \
                             "
RRECOMMENDS_${PN}_append_libc-glibc = " \
                             glibc-charmap-ibm850 \
                             glibc-gconv-ibm850 \
                             glibc-gconv-unicode \
                             glibc-gconv-utf-32 \
                             glibc-charmap-utf-8 \
                             glibc-localedata-en-us \
                             "

INSANE_SKIP_${PN} = "already-stripped"
