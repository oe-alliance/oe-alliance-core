DEFAULT_PREFERENCE = "-1"

FILESPATH =. "${FILE_DIRNAME}/kodi-${PV}:"

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
	python-native \
	samba \
	sqlite3 \
	taglib \
	virtual/egl \
	virtual/libsdl \
	wavpack \
	yajl \
	zlib \
	${@enable_glew(bb, d)} \
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

inherit autotools-brokensep gettext pythonnative

ACCEL ?= ""
ACCEL_x86 = "vaapi vdpau"
ACCEL_x86-64 = "vaapi vdpau"

PACKAGECONFIG ??= "${ACCEL}"
PACKAGECONFIG_append += "${@bb.utils.contains('DISTRO_FEATURES', 'x11', ' x11', '', d)}"
PACKAGECONFIG_append += "${@bb.utils.contains('DISTRO_FEATURES', 'opengl', ' opengl', ' openglesv2', d)}"

PACKAGECONFIG[opengl] = "--enable-gl,--enable-gles,"
PACKAGECONFIG[openglesv2] = "--enable-gles,--enable-gl,virtual/egl"
PACKAGECONFIG[vaapi] = "--enable-vaapi,--disable-vaapi,libva"
PACKAGECONFIG[vdpau] = "--enable-vdpau,--disable-vdpau,libvdpau"
PACKAGECONFIG[mysql] = "--enable-mysql,--disable-mysql,mysql5"
PACKAGECONFIG[x11] = "--enable-x11,--disable-x11,libxinerama libxmu libxrandr libxtst"
PACKAGECONFIG[lcms] = "--enable-lcms2,--disable-lcms2,lcms"

EXTRA_OECONF_append_rpi = " --disable-openmax --enable-player=omxplayer --with-platform=raspberry-pi2"
EXTRA_OECONF_remove_aarch64 = "--with-platform=raspberry-pi2"
LDFLAGS_append_rpi = " -lvchostif "

S = "${WORKDIR}/xbmc-${PV}-${BRANCH}"

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
	--enable-optimizations \
	--disable-lirc \
	--disable-dbus \
	--disable-gtest \
	--enable-libusb \
	--disable-ssh \
	--enable-pulse=no \
	--disable-libcec \
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
export PARALLEL_MAKE

def enable_glew(bb, d):
	if bb.utils.contains('PACKAGECONFIG', 'x11', True, False, d) and bb.utils.contains('DISTRO_FEATURES', 'opengl', True, False, d):
		return "glew"
	return ""

do_configure() {
	( for i in $(find ${S} -name "configure.*" ) ; do
		cd $(dirname $i) && gnu-configize --force || true
	done )
	make -C tools/depends/target/crossguid PREFIX=${STAGING_DIR_HOST}${prefix} BASE_URL=http://ftp.vim.org/mediaplayer/xbmc/build-deps/sources

	BOOTSTRAP_STANDALONE=1 make -f bootstrap.mk JSON_BUILDER="${STAGING_BINDIR_NATIVE}/JsonSchemaBuilder"
	BOOTSTRAP_STANDALONE=1 make -f codegenerator.mk JSON_BUILDER="${STAGING_BINDIR_NATIVE}/JsonSchemaBuilder"
	oe_runconf
}

INSANE_SKIP_${PN} = "rpaths"

FILES_${PN} = "${libdir}/kodi ${libdir}/xbmc"
FILES_${PN} += "${bindir}/kodi ${bindir}/xbmc"
FILES_${PN} += "${datadir}/icons ${datadir}/kodi ${datadir}/xbmc"
FILES_${PN} += "${bindir}/kodi-standalone ${bindir}/xbmc-standalone ${datadir}/xsessions"
FILES_${PN}-dev = "${includedir}"
FILES_${PN}-dbg += "${libdir}/kodi/.debug ${libdir}/kodi/*/.debug ${libdir}/kodi/*/*/.debug ${libdir}/kodi/*/*/*/.debug ${datadir}/applications"

# xbmc uses some kind of dlopen() method for libcec so we need to add it manually
# OpenGL builds need glxinfo, that's in mesa-demos
RRECOMMENDS_${PN}_append = " \
	libcec \
	python \
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
	libcurl \
	lsb \
	os-release \
	${@bb.utils.contains('PACKAGECONFIG', 'x11', 'xrandr xdpyinfo', '', d)} \
	"

RRECOMMENDS_${PN}_append_libc-glibc = " \
	glibc-charmap-ibm850 \
	glibc-gconv-ibm850 \
	glibc-gconv-unicode \
	glibc-gconv-utf-32 \
	glibc-charmap-utf-8 \
	glibc-localedata-en-us \
	"
