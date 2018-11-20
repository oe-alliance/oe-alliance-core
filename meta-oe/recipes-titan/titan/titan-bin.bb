SUMMARY = "TitanNit is a fast Linux Framebuffer Gui"
MAINTAINER = "TitanNit Team"
SECTION = "multimedia"
LICENSE = "GPLv2"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PKGV = "2.0+svnr${GITPKGV}"
PV = "2.0+svnr${SRCPV}"
PR = "r3"


SRC_URI = "svn://sbnc.dyndns.tv/svn/;module=titan;protocol=http"

DEPENDS = " \
	libpng \
	jpeg \
	rtmpdump \
	libdreamdvd \
	exteplayer3 \
	titan-libeplayer3 \
	${@bb.utils.contains("MACHINE_FEATURES", "uianimation", "vuplus-libgles-${MACHINE} libvugles2" , "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "hiaccel", "dinobot-libs-${MACHINE}" , "", d)} \
	"

DEPENDS_append_sh4 = " \
	libmme-image \
	"

RDEPENDS_${PN} = " \
	glibc-gconv-iso8859-15 \
	hotplug-e2-helper \
	gawk \
	bash \
	${@bb.utils.contains("MACHINE_FEATURES", "uianimation", "vuplus-libgles-${MACHINE} libvugles2" , "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "hiaccel", "dinobot-libs-${MACHINE}" , "", d)} \
	"

RRECOMMENDS_append_sh4_${PN} = " \
	libmme-host \
	"

S = "${WORKDIR}/titan"

CFLAGS_append = " \
	-I${STAGING_DIR_TARGET}/usr/include \
	-I${STAGING_DIR_TARGET}/usr/include/freetype2 \
	-I${STAGING_DIR_TARGET}/usr/include/openssl \
	-I${STAGING_DIR_TARGET}/usr/include/dreamdvd \
	-I${STAGING_DIR_TARGET}/usr/include/libdreamdvd \
	-I${WORKDIR}/titan/libdreamdvd \
	-I${WORKDIR}/titan/titan \
	-I${WORKDIR}/titan/titan/include \
	-I${WORKDIR}/titan/libeplayer3/include \
	"
CFLAGS_append_sh4 = " \
	-I${STAGING_DIR_TARGET}/usr/include/libmmeimage \
	-I${STAGING_KERNEL_DIR}/extra/bpamem \
	"

CFLAGS_append_arm = " -DARM -DDREAMBOX -DMIPSEL"
CFLAGS_append_arm_dm900 = " -DCONFIG_ION"
CFLAGS_append_arm_dm920 = " -DCONFIG_ION"
CFLAGS_append_mipsel = " -DMIPSEL"
CFLAGS_append_mipsel_dm7020hd = " -DDREAMBOX"
CFLAGS_append_mipsel_dm520 = " -DDREAMBOX -DCONFIG_ION"
CFLAGS_append_mipsel_dm525 = " -DDREAMBOX -DCONFIG_ION"

CFLAGS_append_sh4 = " -DEXTEPLAYER3 -DEPLAYER3 -DSH4 -DSH4NEW -DCAMSUPP -Os -export-dynamic -Wall -Wno-unused-but-set-variable -Wno-implicit-function-declaration"
CFLAGS_append_mipsel = " -DEXTEPLAYER3 -DEPLAYER3 -DCAMSUPP -Os -mhard-float -export-dynamic -Wall -Wno-unused-but-set-variable -Wno-implicit-function-declaration -Wno-unused-variable -Wno-format-overflow -Wno-format-truncation -Wno-nonnull -Wno-restrict"
CFLAGS_append_arm = " -DEXTEPLAYER3 -DEPLAYER3 -DCAMSUPP -Os -mhard-float -export-dynamic -Wall -Wno-unused-but-set-variable -Wno-implicit-function-declaration -Wno-unused-variable -Wno-format-overflow -Wno-format-truncation -Wno-nonnull -Wno-restrict"

do_compile() {
	cd ${WORKDIR}/titan/titan

	SVNVERSION=`svn info http://sbnc.dyndns.tv/svn/titan | grep Revision | sed s/'Revision: '//g`
	echo SVNVERSION: ${SVNVERSION}

#    	svn update

	if [ ${HOST_SYS} = "sh4-oe-linux" ];then
		cp Makefile.am.sh4.4.3 Makefile.am
	elif [ ${HOST_SYS} = "arm-oe-linux-gnueabi" ];then
		cp Makefile.am.arm.4.3 Makefile.am
	else
		cp Makefile.am.mipsel.4.3 Makefile.am
	fi

	libtoolize --force
	aclocal -I ${STAGING_DIR_TARGET}/usr/share/aclocal
	autoconf
	automake --foreign --add-missing
	./configure --host=${HOST_SYS} --build=${BUILD_SYS}

	make -f Makefile titan
#	${STRIP} titan
}

FILES_${PN} = " \
	/etc \
	/etc/titan.restore \
	/etc/titan.restore/mnt/enigma2 \
	/etc/titan.restore/mnt/config \
	/etc/titan.restore/mnt/settings \
	/etc/titan.restore/mnt/network \
	/etc/titan.restore/var/etc/ipkg \
	/etc/titan.restore/var/etc/tuxbox \
	/etc/titan.restore/var/etc/titan \
	/etc/titan.restore/var/etc/boot \
	/etc/titan.restore/var/etc/autostart \
	/etc/titan.restore/var/etc/network \
	/etc/titan.restore/var/etc/ipkg \
	/etc/init.d \
	/etc/mdev \
	/etc/network \
	/sbin \
	/usr/local/bin \
	/var/etc \
	/var/etc/codepages \
	/var/usr \
	/var/usr/local \
	/var/usr/share \
	/var/usr/local/share \
	/var/usr/local/share/titan \
	/var/usr/local/share/titan/web \
	/var/usr/local/share/titan/skin \
	/var/usr/local/share/titan/netsurf \
	/var/usr/local/share/titan/picons \
	/var/usr/local/share/titan/plugins \
	/var/usr/local/share/titan/help \
	/var/usr/local/share/titan/po \
	/var/usr/local/share/titan/web/tmp \
	/var/usr/local/share/titan/web/img \
	/var/usr/local/share/titan/skin/default \
	/var/usr/local/share/titan/skin/default/icons \
	/var/usr/local/share/titan/skin/default/infobar \
	/var/usr/local/share/titan/skin/default/png \
	/var/usr/local/share/titan/skin/default/oled \
	/var/usr/local/share/titan/skin/default/skin \
	/var/usr/local/share/titan/skin/default/preview \
	/var/usr/local/share/titan/netsurf/Choices \
	/var/usr/local/share/titan/netsurf/gtk \
	/var/usr/local/share/titan/netsurf/framebuffer \
	/var/usr/local/share/titan/netsurf/!NetSurf \
	/var/usr/local/share/titan/netsurf/gtk/res \
	/var/usr/local/share/titan/netsurf/gtk/res/icons \
	/var/usr/local/share/titan/netsurf/gtk/res/themes \
	/var/usr/local/share/titan/netsurf/gtk/res/de \
	/var/usr/local/share/titan/netsurf/gtk/res/en \
	/var/usr/local/share/titan/netsurf/gtk/res/throbber \
	/var/usr/local/share/titan/netsurf/gtk/res/ja \
	/var/usr/local/share/titan/netsurf/gtk/res/nl \
	/var/usr/local/share/titan/netsurf/gtk/res/fr \
	/var/usr/local/share/titan/netsurf/gtk/res/it \
	/var/usr/local/share/titan/netsurf/framebuffer/res \
	/var/usr/local/share/titan/netsurf/framebuffer/res/icons \
	/var/usr/local/share/titan/netsurf/framebuffer/res/fonts \
	/var/usr/local/share/titan/netsurf/framebuffer/res/throbber \
	/var/usr/local/share/titan/netsurf/framebuffer/res/pointers \
	/var/usr/local/share/titan/netsurf/!NetSurf/Docs \
	/var/usr/local/share/titan/netsurf/!NetSurf/Resources \
	/var/usr/local/share/titan/netsurf/!NetSurf/Resources/de \
	/var/usr/local/share/titan/netsurf/!NetSurf/Resources/en \
	/var/usr/local/share/titan/netsurf/!NetSurf/Resources/Icons \
	/var/usr/local/share/titan/netsurf/!NetSurf/Resources/ja \
	/var/usr/local/share/titan/netsurf/!NetSurf/Resources/nl \
	/var/usr/local/share/titan/netsurf/!NetSurf/Resources/Fonts \
	/var/usr/local/share/titan/netsurf/!NetSurf/Resources/fr \
	/var/usr/local/share/titan/netsurf/!NetSurf/Resources/it \
	/var/usr/local/share/titan/plugins/tithek \
	/var/usr/local/share/titan/plugins/readerconfig \
	/var/usr/local/share/titan/plugins/browser \
	/var/usr/local/share/titan/plugins/networkbrowser \
	/var/usr/local/share/titan/plugins/imdbapi \
	/var/usr/local/share/titan/plugins/streaminfo \
	/var/usr/local/share/titan/plugins/mc \
	/var/usr/local/share/titan/plugins/weather \
	/var/usr/local/share/titan/plugins/tmdb \
	/var/usr/local/share/titan/plugins/gmediarender \
	/var/usr/local/share/titan/plugins/filemanager \
	/var/usr/local/share/titan/plugins/imdb \
	/var/usr/local/share/titan/help/de \
	/var/usr/local/share/titan/help/en \
	/var/usr/local/share/titan/po/pl \
	/var/usr/local/share/titan/po/lt \
	/var/usr/local/share/titan/po/de \
	/var/usr/local/share/titan/po/ru \
	/var/usr/local/share/titan/po/en \
	/var/usr/local/share/titan/po/el \
	/var/usr/local/share/titan/po/nl \
	/var/usr/local/share/titan/po/vi \
	/var/usr/local/share/titan/po/es \
	/var/usr/local/share/titan/po/fr \
	/var/usr/local/share/titan/po/it \
	/var/usr/share/fonts"


do_install() {
	install -d ${D}/usr/local/bin
	install -m 0755 titan/titan ${D}/usr/local/bin/titan

	cp -r oealliance/* ${D}
	if [ -e ${D}/etc/titan.restore/mnt/config/titan.${MACHINE}.cfg ];then
		cp ${D}/etc/titan.restore/mnt/config/titan.${MACHINE}.cfg ${D}/etc/titan.restore/mnt/config/titan.cfg
	fi
	if [ -e ${D}/etc/titan.restore/mnt/config/rcconfig.${MACHINE} ];then
		cp ${D}/etc/titan.restore/mnt/config/rcconfig.${MACHINE} ${D}/etc/titan.restore/mnt/config/rcconfig
	fi
}
do_install[vardepsexclude] += "DATETIME"


