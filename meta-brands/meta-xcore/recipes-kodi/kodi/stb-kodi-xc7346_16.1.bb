SUMMARY = "Kodi Media Center"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE.GPL;md5=930e2a5f63425d8dd72dbd7391c43c46"

COMPATIBLE_MACHINE = "^(xc7346)$"

FILESPATH =. "${FILE_DIRNAME}/stb-kodi-xc7346-16.1:"

PACKAGE_ARCH = "${MACHINE}"

PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"

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
	bluez5 \
	boost \
	bzip2 \
	curl \
	enca \
	expat \
	faad2 \
	fontconfig \
	fribidi \
	giflib \
	gnutls \
	jasper \
	jpeg \
	libass \
	libcdio \
	libcec \
	libdcadec \
	libmad \
	libmicrohttpd \
	libmms \
	libmodplug \
	libnfs \
	libpcre \
	libplist \
	libpng \
	libsamplerate0 \
	libsquish \
	libssh \
	libtinyxml \
	libusb1 \
	libvorbis \
	libxslt \
	lzo \
	mpeg2dec \
	mysql5 \
	python \
	python-native \
	samba \
	sqlite3 \
	taglib \
	tiff \
	udev \
	wavpack \
	yajl \
	zlib \
	v3d-libgles-${MACHINE} \
	"

BRANCH = "Jarvis"
PR = "r1"

SRC_URI = " \
	https://github.com/xbmc/xbmc/archive/16.1-Jarvis.tar.gz \
	file://0001-Jarvis-16.1-V3D-support.patch \
	file://0002-Fix-nullpadding-issue-when-reading-certain-id3v1-tag.patch \
	"

SRC_URI[md5sum] = "79358ad5f77f42c5498a9412d5a78dd5"
SRC_URI[sha256sum] = "7d82c8aff2715c83deecdf10c566e26105bec0473af530a1356d4c747ebdfd10"

inherit autotools-brokensep gettext pythonnative

S = "${WORKDIR}/xbmc-${PV}-${BRANCH}"

EXTRA_OECONF = " \
	--with-platform=v3d \
	--enable-optimizations \
	--disable-debug \
	--disable-libcap \
	--disable-ccache \
	--disable-mid \
	--enable-libusb \
	--enable-airplay \
	--disable-lirc \
	--disable-libcec \
	--disable-dbus \
	--disable-gtest \
	--enable-texturepacker=no \
	"

BUILD_OPTIMIZATION = "${FULL_OPTIMIZATION}"

# for python modules
export HOST_SYS
export BUILD_SYS
export STAGING_LIBDIR
export STAGING_INCDIR
export PYTHON_DIR
export PARALLEL_MAKE

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

RDEPENDS_${PN}_append = "v3d-libgles-${MACHINE}"

# xbmc uses some kind of dlopen() method for libcec so we need to add it manually
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
	"

RRECOMMENDS_${PN}_append_libc-glibc = " \
	glibc-charmap-ibm850 \
	glibc-gconv-ibm850 \
	glibc-gconv-unicode \
	glibc-gconv-utf-32 \
	glibc-charmap-utf-8 \
	glibc-localedata-en-us \
	"
