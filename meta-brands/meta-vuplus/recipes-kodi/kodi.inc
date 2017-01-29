SUMMARY = "Kodi Media Center"

PACKAGE_ARCH := "${MACHINE_ARCH}"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE.GPL;md5=930e2a5f63425d8dd72dbd7391c43c46"

PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"

DEPENDS = "\
	vuplus-libgles-${MACHINE} \
	libxslt \
	libusb1 \
	libcec \
	libplist \
	expat \
	yajl \
	gperf-native \
	swig-native \
	fribidi \
	mpeg2dec \
	samba \
	fontconfig \
	curl \
	python \
	python-native \
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

PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"

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
	shairplay \
"

BRANCH = "jarvis_vuplus"
SRCREV = "528f9f8abfc045b8cf8edea013aaf19fd02dba09"

PV = "16.1.rc2"
PR = "${BUILD_PR}_${GLPR}_${SRCREV}"

SRC_URI = "\
	git://code.vuplus.com/git/xbmc.git;protocol=http;branch=${BRANCH};tag=${SRCREV} \
	http://archive.vuplus.com/download/build_support/kodi/xbmc-support_${MACHINE}_${GLPR}.tar.gz;name=xbmc-support \
	file://0001-lib-cximage-6.0-fix-compilation-with-gcc6.patch \
"

inherit autotools-brokensep autotools lib_package pkgconfig gettext python-dir

S = "${WORKDIR}/git"

CACHED_CONFIGUREVARS += " \
 ac_cv_path_PYTHON="${STAGING_BINDIR_NATIVE}/python-native/python" \
"

EXTRA_OECONF = " \
	--enable-gles \
	--enable-libusb \
	--enable-airplay \
	--enable-rtmp	\
	--enable-optimizations \
	--disable-optical-drive \
	--disable-ssh \
	--disable-x11 \
	--disable-dbus \
	--disable-pulse \
	--disable-gtest \
	--disable-joystick \
	--disable-alsa \
	--disable-lirc \
	--disable-libcec \
	--disable-texturepacker \
	--disable-debug \
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
	cp -av ${WORKDIR}/xbmc-support/gles_init.* ${WORKDIR}/git/xbmc/windowing/egl/
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
FILES_${PN}-dev = "${includedir}"
FILES_${PN}-bin = "${bindir}/kodi-standalone ${bindir}/xbmc-standalone ${datadir}/xsessions"
FILES_${PN}-dbg += "${libdir}/kodi/.debug ${libdir}/kodi/*/.debug ${libdir}/kodi/*/*/.debug ${libdir}/kodi/*/*/*/.debug ${datadir}/applications"

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
