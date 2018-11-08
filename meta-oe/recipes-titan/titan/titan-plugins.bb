SUMMARY = "titan a simple gst videoplayer"
MAINTAINER = "Duckbox Team"
SECTION = "multimedia"
#PRIORITY = "optional"
LICENSE = "GPLv2"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

SRCREV = "${AUTOREV}"
PV = "2.0+svnr${SRCPV}"
PR = "r1"

SRC_URI = "svn://sbnc.dyndns.tv/svn/;module=titan;protocol=http"

DEPENDS = "libpng jpeg rtmpdump gstreamer gst-plugins-base gst-plugins-good gst-plugins-bad gst-plugins-ugly gst-plugin-subsink libdreamdvd"

S = "${WORKDIR}/titan"

#inherit autotools-brokensep pkgconfig

CFLAGS = "\
	-I${STAGING_DIR_TARGET}/usr/include \
	-I${STAGING_DIR_TARGET}/usr/include/gstreamer-0.10 \
	-I${STAGING_DIR_TARGET}/usr/include/glib-2.0 \
	-I${STAGING_DIR_TARGET}/usr/include/libxml2 \
	-I${STAGING_DIR_TARGET}/usr/lib/glib-2.0/include \
	-I${STAGING_DIR_TARGET}/usr/include/freetype2 \
	-I${STAGING_DIR_TARGET}/usr/include/dreamdvd \
	-I${STAGING_DIR_TARGET}/usr/include/libdreamdvd \	
	-I${WORKDIR}/titan/libdreamdvd \
	-I${WORKDIR}/titan/titan"

LDADD = "-lpthread -ldl -lpng -lfreetype -lgstreamer-0.10 -ldreamdvd -ljpeg"

do_configure_prepend() {
    cd ${S}

	if test -e titan/Makefile.am.mipsel;then
		LIST="`ls */Makefile.am.mipsel`"
		LIST="$LIST `ls */*/Makefile.am.mipsel`"
	
		echo LIST $LIST 
		for ROUND in $LIST;do
			if [ -e $ROUND ];then 
				file=`echo $ROUND | sed 's/.mipsel//'`
				echo mv $ROUND $file
				mv $ROUND $file
			fi
		done
	fi
}

do_configure() {
	svn up
    cd ${S}/plugins
	libtoolize --force
	aclocal -I ${STAGING_DIR_TARGET}/usr/share/aclocal
	autoconf
	automake --foreign --add-missing
	./configure --host=${HOST_SYS} --build=${BUILD_SYS}
}

do_compile() {
    cd ${S}/plugins
    make clean
	make -f Makefile
	${STRIP} ${S}/plugins/*/.libs/*.so
}

FILES_${PN} = "/var/usr/local/share/titan/plugins"

do_install_append() {
	install -d ${D}/var/usr/local/share/titan/plugins
	
	LIST="`cat plugins/Makefile.am | sed 's/\\t\+/ /g' | sed 's/ \\+//g' | sed 's/\\\//g' | grep -v =`"
	echo LIST $LIST
	for ROUND in $LIST;do
		echo ROUND $ROUND
		install -d ${D}/var/usr/local/share/titan/plugins/$ROUND
	    install -m 0644 plugins/$ROUND/.libs/*.so ${D}/var/usr/local/share/titan/plugins/$ROUND

	    if test -e skins/$ROUND/skin;then
			cp -a skins/$ROUND/skin ${D}/var/usr/local/share/titan/plugins/$ROUND/
		fi
	    if test -e skins/$ROUND/skin.xml;then
			install -m 0644 skins/$ROUND/skin.xml ${D}/var/usr/local/share/titan/plugins/$ROUND/
		fi
	    if test -e skins/$ROUND/plugin.png;then
			install -m 0644 skins/$ROUND/plugin.png ${D}/var/usr/local/share/titan/plugins/$ROUND/
		fi
	done

#	cp -a skins/* ${D}/var/usr/local/share/titan/plugins/
}