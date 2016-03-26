SUMMARY = "Kodi Media Center"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE.GPL;md5=930e2a5f63425d8dd72dbd7391c43c46"

DEPENDS = "vuplus-libgles-${MACHINE} libxslt libusb1 libcec libplist expat yajl gperf-native fribidi mpeg2dec samba fontconfig curl python libass libmodplug libmicrohttpd wavpack libmms cmake-native libsdl-image mysql5 sqlite3 libmms faad2 libcdio libpcre boost lzo enca avahi libsamplerate0  bzip2 virtual/libsdl jasper zip-native zlib libtinyxml libtinyxml-native taglib libbluray libshairport librtmp zlib libnfs libxslt libsquish jsonschemabuilder-native ffmpeg-kodi"

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
"

SRCREV = "c42cb0eefa5e011f70780093f0489e4e978a49b6"

PV = "15.2"
PR = "${BUILD_PR}_${GLPR}_${SRCREV}"

#SRC_URI = "git://code.vuplus.com/git/xbmc.git;protocol=http;branch=${BRANCH};tag=${SRCREV}
SRC_URI = "git://github.com/mx3L/xbmc.git;protocol=git;branch=vu_kodi_15_2 \
	http://archive.vuplus.com/download/build_support/xbmc-support_${GLPR}${GLARCH}.tar.gz;name=xbmc-support \
	file://ffmpeg-libs.patch \
"

SRC_URI_append_vusolo4k = " file://arm_support.patch"

inherit autotools-brokensep gettext python-dir

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
	--disable-ssh \
	--disable-x11 \
	--disable-joystick \
	--disable-libcec \
	--disable-lirc \
	--disable-texturepacker \
	--with-ffmpeg=force \
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

# on ARM architectures xbmc will use GLES which will make the regular wrapper fail, so start it directly
#do_install_append_arm() {
#	sed -i -e 's:Exec=xbmc:Exec=${libdir}/xbmc/xbmc.bin:g' ${D}${datadir}/applications/xbmc.desktop
#}

do_install_append(){
	install -d ${D}${bindir}
	install -m 0755 ${WORKDIR}/xbmc-support/xbmc.helper ${D}${bindir}
}

do_package_qa(){
}

FILES_${PN} += "${datadir}/xsessions ${datadir}/icons"
FILES_${PN} += "/usr/bin /usr/share /usr/lib"
FILES_${PN}-dbg += "${libdir}/kodi/.debug ${libdir}/kodi/*/.debug ${libdir}/kodi/*/*/.debug ${libdir}/kodi/*/*/*/.debug"

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

