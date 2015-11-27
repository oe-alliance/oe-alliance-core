SUMMARY = "KODI Media Center"
MAINTAINER = "PB-Powerboard Team"
LICENSE = "GPLv2"
SECTION = "base"
PRIORITY = "required"
PACKAGE_ARCH = "${MACHINE_ARCH}"

#PV = "1"
PR = "r9"

require conf/license/license-gplv2.inc

INHIBIT_PACKAGE_STRIP = "1"


SRC_URI="file://kodi.tgz"

S = "${WORKDIR}"

do_install() {
    install -d ${D}/usr
#    cp -rp ${S}/usr ${D}
    cp -axr ${S}/usr ${D}
     cp -ax ${S}/usr/lib/xbmc ${D}/usr/lib
}

FILES_${PN} = "/usr/bin/* /usr/lib/* /usr/share/*"


DEPENDS_${PN} += " boost bzip2 curl expat flac fontconfig freetype fribidi \
           jasper jpeg libass libbluray libcdio libcec libgcrypt \
           libgpg-error libmad libmicrohttpd libmodplug libnfs libogg \
           libpcre libpng libsamplerate0 libsdl libtheora libtinyxml \
           libungif libvorbis libxml2 libxslt lzo mpeg2dec ncurses \
           openssl python readline rtmpdump sqlite3 taglib tiff wavpack yajl \
           gperf-native swig-native unzip-native zip-native \
           libamplayer virtual/egl \
           libamcodec \
           "


RDEPENDS_${PN} += " python "
RDEPENDS_${PN} += "\
	python-pkgutil \
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

#do_package_qa(){
#}

sysroot_stage_all() {
    :
}

#do_rm_work(){
#}




