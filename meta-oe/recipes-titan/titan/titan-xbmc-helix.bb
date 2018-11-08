SUMMARY = "XBMC Media Center"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE.GPL;md5=930e2a5f63425d8dd72dbd7391c43c46"

#DEPENDS = "libusb1 libcec libplist expat yajl gperf-native libxmu fribidi mpeg2dec ffmpeg samba fontconfig curl python libass libmodplug libmicrohttpd wavpack libmms cmake-native libsdl-image libsdl-mixer virtual/egl mysql5 sqlite3 libmms faad2 libcdio libpcre boost lzo enca avahi libsamplerate0 libxinerama libxrandr libxtst bzip2 virtual/libsdl jasper zip-native zlib libtinyxml libmad"
DEPENDS = "directfb mesa alsa-oss curl libvorbis libusb1 libcec libplist expat yajl gperf-native libxmu fribidi mpeg2dec ffmpeg samba fontconfig curl python libass libmodplug libmicrohttpd wavpack libmms cmake-native libsdl-image libsdl-mixer mysql5 sqlite3 libmms faad2 libcdio libpcre boost lzo enca avahi libsamplerate0 libxinerama libxrandr libxtst bzip2 virtual/libsdl jasper zip-native zlib libtinyxml libmad"

#require recipes/egl/egl.inc

#SRCREV = "7cc53a9a3da77869d1d5d3d3d9971b4bd1641b50"
SRCREV = "${AUTOREV}"

# multiple issues
PNBLACKLIST[xbmc] ?= "/usr/include/c++/ctime:70:11: error: '::gmtime' has not been declared"

PV = "auto+helix+gitr${SRCPV}"
PR = "r14"
NATIVEGLES_PR="20141202_p0"

SRC_URI = "git://github.com/xbmc/xbmc.git;branch=Helix \
           file://0001-configure-don-t-run-python-distutils-to-find-STAGING.patch \
           file://configure.in-helix.patch \
           file://EGLNativeTypeDvbBox.patch \
           http://archive.vuplus.com/download/build_support/xbmc-support_${NATIVEGLES_PR}.tar.gz;name=xbmc-support \
"

# file://EGLNativeTypeDvbBox.patch
# http://archive.vuplus.com/download/build_support/xbmc-support_${NATIVEGLES_PR}.tar.gz;name=xbmc-support

inherit autotools gettext python-dir

S = "${WORKDIR}/git"

# breaks compilation
CCACHE = ""

CACHED_CONFIGUREVARS += " \
    ac_cv_path_PYTHON="${STAGING_BINDIR_NATIVE}/python-native/python" \
"

PACKAGECONFIG ??= "${@base_contains('DISTRO_FEATURES', 'opengl', 'opengl', 'openglesv2', d)}"
PACKAGECONFIG[opengl] = "--enable-gl,--enable-gles,glew"
PACKAGECONFIG[openglesv2] = "--enable-gles,--enable-gl,"

FULL_OPTIMIZATION_armv7a = "-fexpensive-optimizations -fomit-frame-pointer -O4 -ffast-math"
BUILD_OPTIMIZATION = "${FULL_OPTIMIZATION}"

EXTRA_OECONF = " \
	--with-arch=${TARGET_ARCH} \
	--disable-rpath \
	--enable-gles \
	--enable-libusb \
	--enable-airplay \
	--enable-directfb \
	--disable-optical-drive \
	--disable-ssh \
	--disable-debug \
	--disable-x11 \
	--disable-sdl \
	--disable-joystick \
	--disable-alsa \
	--disable-libcec \
	--enable-rtmp	\
	--enable-external-libraries \
	--enable-external_ffmpeg \
	--disable-gnutls \
	--disable-texturepacker \
	--with-platform=dvbbox \
"

RRECOMMENDS_${PN}_append_libc-glibc = " glibc-charmap-ibm850 glibc-gconv-ibm850"

# for python modules
export HOST_SYS
export BUILD_SYS
export STAGING_LIBDIR
export STAGING_INCDIR
export PYTHON_DIR

# configuration settings
#ffmpg_config = "--prefix=$(PREFIX) --extra-version="xbmc-$(VERSION)"
#ffmpg_config += "--cc=$(CC) --cxx=$(CXX)"
ffmpg_config = "--disable-devices --disable-doc"
ffmpg_config += "--disable-ffplay --disable-ffmpeg"
ffmpg_config += "--disable-ffprobe --disable-ffserver"
ffmpg_config += "--enable-gpl --enable-runtime-cpudetect"
ffmpg_config += "--enable-postproc --enable-pthreads"
ffmpg_config += "--enable-muxer=spdif --enable-muxer=adts"
ffmpg_config += "--enable-muxer=asf --enable-muxer=ipod"
ffmpg_config += "--enable-encoder=ac3 --enable-encoder=aac"
ffmpg_config += "--enable-encoder=wmav2 --enable-protocol=http"
#  ffmpg_config += "--arch=$(CPU) --enable-cross-compile"
#  ffmpg_config += "--target-os=$(OS) --cpu=$(CPU)"
ffmpg_config += "--enable-vdpau --enable-vaapi --enable-gnutls"
ffmpg_config += "--enable-libvorbis --enable-muxer=ogg --enable-encoder=libvorbis"
ffmpg_config += "--disable-mips32r2 --disable-mipsdspr1 --disable-mipsdspr2"
#  ffmpg_config += "--disable-debug"


EXTRA_OECONF_FFMPEG = " \
        \
        --cross-prefix=${TARGET_PREFIX} \
        --prefix=${prefix}/ \
        \
        --extra-cflags="--sysroot=${STAGING_DIR_TARGET}" \
        --extra-ldflags="--sysroot=${STAGING_DIR_TARGET}" \
        --enable-hardcoded-tables \
		--pkg-config="pkg-config" \
		--target-os=linux \
		--arch=${TARGET_ARCH} \
		--enable-postproc \
        ${EXTRA_FFCONF} \
"

do_configure() {
	# cleanup
	rm -rf ${STAGING_DIR_TARGET}/usr/lib/xbmc
	rm -rf ${STAGING_DIR_TARGET}/usr/share/xbmc
	rm -rf ${STAGING_DIR_TARGET}/usr/lib/kodi
	rm -rf ${STAGING_DIR_TARGET}/usr/share/kodi
	rm -rf ${STAGING_DIR_TARGET}/usr/share/xsessions/kodi.desktop 
	rm -rf ${STAGING_DIR_TARGET}/usr/share/xsessions/xbmc.desktop
	rm -rf ${STAGING_DIR_TARGET}/usr/share/icons/hicolor/48x48/apps/kodi.png
	rm -rf ${STAGING_DIR_TARGET}/usr/share/icons/hicolor/32x32/apps/kodi.png
	rm -rf ${STAGING_DIR_TARGET}/usr/share/icons/hicolor/128x128/apps/kodi.png
	rm -rf ${STAGING_DIR_TARGET}/usr/share/icons/hicolor/16x16/apps/kodi.png
	rm -rf ${STAGING_DIR_TARGET}/usr/share/icons/hicolor/22x22/apps/kodi.png
	rm -rf ${STAGING_DIR_TARGET}/usr/share/icons/hicolor/64x64/apps/kodi.png
	rm -rf ${STAGING_DIR_TARGET}/usr/share/icons/hicolor/256x256/apps/kodi.png
	rm -rf ${STAGING_DIR_TARGET}/usr/share/icons/hicolor/24x24/apps/kodi.png
	rm -rf ${STAGING_DIR_TARGET}/usr/include/kodi

	FVERSION=`cat ${S}/tools/depends/target/ffmpeg/FFMPEG-VERSION | grep VERSION= | cut -d "=" -f2`
	echo "FVERSION: $FVERSION"
#    ${S}/tools/depends/target/ffmpeg/autobuild.sh -d --arch=${TARGET_ARCH} --prefix=${S}/tools/depends/target/ffmpeg/ffmpeg-${FVERSION}

	cd ${S}/tools/depends/target/ffmpeg
	BASE_URL=$(grep "BASE_URL=" FFMPEG-VERSION | sed 's/BASE_URL=//g')
	FVERSION=$(grep "VERSION=" FFMPEG-VERSION | sed 's/VERSION=//g')
	echo "FVERSION: $FVERSION"
	ARCHIVE=ffmpeg-${FVERSION}.tar.gz
	wget ${BASE_URL}/${FVERSION}.tar.gz -O ${ARCHIVE}
#	wget https://github.com/xbmc/FFmpeg/archive/2.4.6-Helix.tar.gz

	tar -zxvf ${S}/tools/depends/target/ffmpeg/ffmpeg-${FVERSION}.tar.gz -C ${S}/tools/depends/target/ffmpeg
	cd ${S}/tools/depends/target/ffmpeg/FFmpeg-${FVERSION}
	VERSION=`cat ${S}/version.txt | grep ADDON_API | cut -d " " -f2`
	echo "VERSION: $VERSION"
	THREADS=`grep -c "^processor" /proc/cpuinfo`
	echo "THREADS: $THREADS"

	${S}/tools/depends/target/ffmpeg/FFmpeg-${FVERSION}/configure ${EXTRA_OECONF_FFMPEG} ${ffmpg_config} --extra-version="xbmc-${VERSION}"
	make -j ${THREADS}
	make install DESTDIR=${STAGING_DIR_TARGET}

	cd ${S}/tools/depends/native/JsonSchemaBuilder/src
	libtoolize --force
	aclocal -I ${STAGING_DIR_TARGET}/usr/share/aclocal
	autoconf
	automake --foreign --add-missing
	./configure --host=${HOST_SYS} --build=${BUILD_SYS} --prefix=${STAGING_DIR}
	make

    cd ${S}

    ./bootstrap
    oe_runconf
}

do_configure_prepend(){
	cp -av ${WORKDIR}/xbmc-support/gles_init.* ${WORKDIR}/git/xbmc/windowing/egl/
    cd ${S}
}

#PARALLEL_MAKE = " "

do_compile_prepend() {
    for i in $(find . -name "Makefile") ; do
        sed -i -e 's:I/usr/include:I${STAGING_INCDIR}:g' $i
    done

    for i in $(find . -name "*.mak*" -o    -name "Makefile") ; do
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

# on ARM architectures xbmc will use GLES which will make the regular wrapper fail, so start it directly
do_install_append_arm() {
    sed -i -e 's:Exec=xbmc:Exec=${libdir}/xbmc/xbmc.bin:g' ${D}${datadir}/applications/xbmc.desktop
}

do_install_append(){
#	install -d ${D}${bindir}
#	install -m 0755 ${WORKDIR}/xbmc-support/xbmc.helper ${D}${bindir}
}

do_package_qa(){
}

FILES_${PN} += "/usr/bin /usr/share /usr/lib"
FILES_${PN}-dbg += "${libdir}/xbmc/.debug ${libdir}/xbmc/*/.debug ${libdir}/xbmc/*/*/.debug ${libdir}/xbmc/*/*/*/.debug"

# xbmc uses some kind of dlopen() method for libcec so we need to add it manually
# OpenGL builds need glxinfo, that's in mesa-demos
RRECOMMENDS_${PN}_append = " libcec \
                             python \
                             python-lang \
                             python-re \
                             python-netclient \
                             libcurl \
                             xdpyinfo \
                             ${@base_contains('DISTRO_FEATURES', 'opengl', 'mesa-demos', '', d)} \
"
RRECOMMENDS_${PN}_append_libc-glibc = " glibc-charmap-ibm850 glibc-gconv-ibm850"