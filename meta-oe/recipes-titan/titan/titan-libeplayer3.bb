SUMMARY = "libeplayer3 mediaplayer lib and console"
DESCRIPTION = "Core of movie player for Titan the libeplayer using the ffmpeg solution"
MAINTAINER = "TitanNit Team"
SECTION = "multimedia"

LICENSE = "GPLv2"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

#inherit autotools pkgconfig
inherit pkgconfig

SRCREV = "${AUTOREV}"
PKGV = "2.0+git${GITPKGV}"
PV = "2.0+svnr${SRCPV}"
PR = "r3"

SRC_URI = "svn://sbnc.dyndns.tv/svn/titan;module=libeplayer3;protocol=http"
#    file://fix_uint_include.patch;patch=1

DEPENDS = "ffmpeg libass"
RDEPENDS_${PN} = "ffmpeg"

SSTATE_DUPWHITELIST += "${STAGING_DIR_TARGET}/usr/lib/libeplayer3.so.0.0.0"

S = "${WORKDIR}/libeplayer3"

do_compile() {
	cd ${WORKDIR}/libeplayer3

	if [ ${HOST_SYS} = "sh4-oe-linux" ];then
		cp Makefile.am.sh4 Makefile.am
	elif [ ${HOST_SYS} = "arm-oe-linux-gnueabi" ];then
		cp Makefile.am.arm Makefile.am
	else
		cp Makefile.am.mipsel Makefile.am
	fi
#    ${CC} ${SOURCE_FILES} -D_FILE_OFFSET_BITS=64 -D_LARGEFILE64_SOURCE -D_LARGEFILE_SOURCE -I${S}/include -I${D}/${libdir} -I${D}/${includedir} -lpthread -lavformat -lavcodec -lavutil -lswresample -o exteplayer3

	libtoolize --force
	aclocal -I ${STAGING_DIR_TARGET}/usr/share/aclocal
	autoconf
	automake --foreign --add-missing
	./configure --host=${HOST_SYS} --build=${BUILD_SYS}

	make -f Makefile
    ${STRIP} .libs/eplayer3
    ${STRIP} .libs/libeplayer3.so.0.0.0

	cp -a ${WORKDIR}/libeplayer3/.libs/* ${STAGING_DIR_TARGET}/usr/lib/
#	cp -a ${WORKDIR}/libeplayer3/include ${STAGING_DIR_TARGET}/usr/include/eplayer3
}

FILES_${PN} = "/usr/bin"
FILES_${PN} += "/usr/lib"

do_install_append() {
    install -d ${D}/usr/bin
    install -d ${D}/usr/lib
    install -m 0755 .libs/eplayer3 ${D}/usr/bin
    install -m 0755 .libs/libeplayer3.so.0.0.0 ${D}/usr/lib 
}
