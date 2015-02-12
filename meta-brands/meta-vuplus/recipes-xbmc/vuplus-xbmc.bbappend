FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SUMMARY = "XBMC Media Center"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE.GPL;md5=6eb631b6da7fdb01508a80213ffc35ff"

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
	lzma \
	tiff \
	yajl \
	libxslt \
	libupnp \
	libplist \
	librtmp \
	libbluray \
	libnfs \
"

BUILD_PR="r1"
NATIVEGLES_PR="20141202_p0"

BRANCH = "gotham_vuplus"
SRCREV = "a4cee0ded4d72572be519ffe8c3aad329113e10a"

PV = "13.2"
PR = "${BUILD_PR}_${NATIVEGLES_PR}_${SRCREV}"

SRC_URI = "git://code.vuplus.com/git/xbmc.git;protocol=http;branch=${BRANCH};tag=${SRCREV} \
	http://archive.vuplus.com/download/build_support/xbmc-support_${NATIVEGLES_PR}.tar.gz;name=xbmc-support \
"
S = "${WORKDIR}/git"

EXTRA_OECONF = " \
	--disable-rpath \
	--enable-gles \
	--enable-libusb \
	--enable-airplay \
	--disable-optical-drive \
	--enable-external-libraries \
	--disable-ssh \
	--enable-external_ffmpeg \
	--disable-x11 \
	--disable-sdl \
	--disable-joystick \
	--disable-alsa \
	--disable-libcec \
	--enable-rtmp	\
	--disable-gnutls \
	--disable-texturepacker \
	--with-platform=dvbbox \
"

do_configure_prepend(){
	cp -av ${WORKDIR}/xbmc-support/gles_init.* ${WORKDIR}/git/xbmc/windowing/egl/
    cd ${S}
}

do_install_prepend(){
    cd ${S}
}

do_compile_prepend(){
    cd ${S}
}

do_install_append(){
	install -d ${D}${bindir}
	install -m 0755 ${WORKDIR}/xbmc-support/xbmc.helper ${D}${bindir}
}

do_package_qa(){
}

PARALLEL_MAKE = " -j8 "

FILES_${PN} += "/usr/bin /usr/share /usr/lib"

SRC_URI[xbmc-support.md5sum] = "9d0c73505484823e3816b5577f28a8a8"
SRC_URI[xbmc-support.sha256sum] = "5483e24db81efca03120dbf0ef4cc423d2665ebc4d52149be15d75a0ae9b626d"

#COMPATIBLE_MACHINE = "^(vusolose|vusolo2|vuduo2)$"