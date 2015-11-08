SUMMARY = "XBMC Media Center"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE.GPL;md5=6eb631b6da7fdb01508a80213ffc35ff"

PROVIDES += "virtual/xbmc"
RPROVIDES_${PN} += "virtual/xbmc"

DEPENDS = "vuplus-libgles-${MACHINE} libxslt libusb1 libcec libplist expat yajl gperf-native fribidi mpeg2dec samba fontconfig curl python libass libmodplug libmicrohttpd wavpack libmms cmake-native libsdl-image libsdl-mixer mysql5 sqlite3 libmms faad2 libcdio libpcre boost lzo enca avahi libsamplerate0  bzip2 virtual/libsdl jasper zip-native zlib libtinyxml taglib libbluray libshairport librtmp zlib libnfs libxslt"

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
	libcurl \
"

BRANCH = "gotham_vuplus"
SRCREV = "5ae20573a048d4ae67e23c0957b7efaa62179c34"

PV = "13.2"
PR = "${BUILD_PR}_${GLPR}_${SRCREV}"

SRC_URI = "git://code.vuplus.com/git/xbmc.git;protocol=http;branch=${BRANCH};tag=${SRCREV} \
	http://archive.vuplus.com/download/build_support/xbmc-support_${GLPR}${GLARCH}.tar.gz;name=xbmc-support \
"

inherit autotools-brokensep gettext python-dir

S = "${WORKDIR}/git"

CACHED_CONFIGUREVARS += " \
 ac_cv_path_PYTHON="${STAGING_BINDIR_NATIVE}/python-native/python" \
"

EXTRA_OECONF = " \
	--enable-gles \
	--enable-libusb \
	--enable-airplay \
	--disable-optical-drive \
	--enable-external-libraries \
	--disable-ssh \
	--disable-x11 \
	--disable-sdl \
	--disable-joystick \
	--disable-alsa \
	--disable-libcec \
	--enable-rtmp	\
	--disable-texturepacker \
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
FILES_${PN}-dbg += "${libdir}/xbmc/.debug ${libdir}/xbmc/*/.debug ${libdir}/xbmc/*/*/.debug ${libdir}/xbmc/*/*/*/.debug"

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

