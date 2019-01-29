SUMMARY = "TitanNit is a fast Linux Framebuffer Gui"
MAINTAINER = "TitanNit Team"
SECTION = "plugins"
#PRIORITY = "optional"
LICENSE = "GPLv2"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

SRCREV = "${AUTOREV}"
PV = "2.0+svnr${SRCPV}"
PR = "r1"

SRC_URI = "svn://sbnc.dyndns.tv/svn/;module=titan;protocol=http"

DEPENDS = "titan \
	python-pyopenssl \
	python-gdata \
	streamripper \
	python-mutagen \
	python-twisted \
	python-daap \
	python-google-api-client \
	python-httplib2 \
	python-youtube-dl \
	libtirpc \
	"

RDEPENDS_${PN} = "python-ctypes"

S = "${WORKDIR}/titan"

#inherit autotools-brokensep pkgconfig

CFLAGS = "\
	-I${STAGING_DIR_TARGET}/usr/include \
	-I${STAGING_DIR_TARGET}/usr/include/curl \
	-I${STAGING_DIR_TARGET}/usr/include/python2.7 \
	-include Python.h \
	-I${STAGING_DIR_TARGET}/usr/include/tirpc \
	-I${STAGING_DIR_TARGET}/usr/include/gstreamer-0.10 \
	-I${STAGING_DIR_TARGET}/usr/include/glib-2.0 \
	-I${STAGING_DIR_TARGET}/usr/include/libxml2 \
	-I${STAGING_DIR_TARGET}/usr/lib/glib-2.0/include \
	-I${STAGING_DIR_TARGET}/usr/include/freetype2 \
	-I${STAGING_DIR_TARGET}/usr/include/dreamdvd \
	-I${STAGING_DIR_TARGET}/usr/include/libdreamdvd \	
	-I${WORKDIR}/titan/libdreamdvd \
	-I${WORKDIR}/titan/titan"

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
CFLAGS_append_arm = "${@bb.utils.contains('GST_VERSION', '1.0', ' \
	-I${STAGING_DIR_TARGET}/usr/include \
	-I${STAGING_DIR_TARGET}/usr/lib/gstreamer-1.0/include \
	-I${STAGING_DIR_TARGET}/usr/include/gstreamer-1.0 \
	-I${STAGING_DIR_TARGET}/usr/include/glib-2.0 \
	-I${STAGING_DIR_TARGET}/usr/include/libxml2 \
	-I${STAGING_DIR_TARGET}/usr/lib/glib-2.0/include \
	-I${STAGING_DIR_TARGET}/usr/include/freetype2 \
	-I${STAGING_DIR_TARGET}/usr/include/dreamdvd \
	-I${STAGING_DIR_TARGET}/usr/include/libdreamdvd \	
	-I${WORKDIR}/titan/libdreamdvd \
	-I${WORKDIR}/titan/titan \
    ', ' \
	-I${STAGING_DIR_TARGET}/usr/include \
	-I${STAGING_DIR_TARGET}/usr/include/gstreamer-0.10 \
	-I${STAGING_DIR_TARGET}/usr/include/glib-2.0 \
	-I${STAGING_DIR_TARGET}/usr/include/libxml2 \
	-I${STAGING_DIR_TARGET}/usr/lib/glib-2.0/include \
	-I${STAGING_DIR_TARGET}/usr/include/freetype2 \
	-I${STAGING_DIR_TARGET}/usr/include/dreamdvd \
	-I${STAGING_DIR_TARGET}/usr/include/libdreamdvd \	
	-I${WORKDIR}/titan/libdreamdvd \
	-I${WORKDIR}/titan/titan \
', d)}"

CFLAGS_append_sh4 = " \
	-I${STAGING_DIR_TARGET}/usr/include/libmmeimage \
	-I${STAGING_KERNEL_DIR}/extra/bpamem \
	"

CFLAGS_append_sh4 = " -DOEBUILD -DEXTEPLAYER3 -DEPLAYER3 -DSH4 -DSH4NEW -DCAMSUPP -Os -export-dynamic -Wall -Wno-unused-but-set-variable -Wno-implicit-function-declaration"
CFLAGS_append_mipsel = " -DOEBUILD -DEXTEPLAYER3 -DEPLAYER3 -DCAMSUPP -Os -mhard-float -export-dynamic -Wall -Wno-unused-but-set-variable -Wno-implicit-function-declaration -Wno-unused-variable -Wno-format-overflow -Wno-format-truncation -Wno-nonnull -Wno-restrict"
CFLAGS_append_arm = " -DOEBUILD -DEXTEPLAYER3 -DEPLAYER3 -DCAMSUPP -Os -mhard-float -export-dynamic -Wall -Wno-unused-but-set-variable -Wno-implicit-function-declaration -Wno-unused-variable -Wno-format-overflow -Wno-format-truncation -Wno-nonnull -Wno-restrict"

LDFLAGS_prepend = " -lcurl "

do_configure() {
	cd ${S}/plugins

	SVNVERSION=`echo ${WORKDIR} | sed -nr 's/.*svnr([^.*]+)-.*/\1/p'`
	echo SVNVERSION: ${SVNVERSION}

	sed "s/^#define PLUGINVERSION .*/#define PLUGINVERSION $SVNVERSION/" -i  ../titan/struct.h
	cat ../titan/struct.h | grep "define PLUGINVERSION"

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

FILES_${PN} = "/usr/local/share/titan/plugins"

do_install_append() {
	install -d ${D}/usr/local/share/titan/plugins
	
	LIST="`cat plugins/Makefile.am | sed 's/\\t\+/ /g' | sed 's/ \\+//g' | sed 's/\\\//g' | grep -v =`"
	echo LIST $LIST
	for ROUND in $LIST;do
		echo ROUND $ROUND
		install -d ${D}/usr/local/share/titan/plugins/$ROUND
		install -m 0644 plugins/$ROUND/.libs/*.so ${D}/usr/local/share/titan/plugins/$ROUND

		if test -e skins/$ROUND/skin;then
			cp -a skins/$ROUND/skin ${D}/usr/local/share/titan/plugins/$ROUND/
		fi
		if test -e skins/$ROUND/skin.xml;then
			install -m 0644 skins/$ROUND/skin.xml ${D}/usr/local/share/titan/plugins/$ROUND/
		fi
		if test -e skins/$ROUND/plugin.png;then
			install -m 0644 skins/$ROUND/plugin.png ${D}/usr/local/share/titan/plugins/$ROUND/
		fi
	done
}

python populate_packages_prepend() {
    titan_plugindir = bb.data.expand('/usr/local/share/titan/plugins', d)
    do_split_packages(d, titan_plugindir, '^(\w+)/[a-zA-Z0-9_]+.*$', 'titan-plugin-%s', '%s', recursive=True, match_path=True, prepend=True, extra_depends="titan")
    do_split_packages(d, titan_plugindir, '^(\w+)/.*\.h$', 'titan-plugin-%s-src', '%s (source files)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, titan_plugindir, '^(\w+)/.*\.la$', 'titan-plugin-%s-dev', '%s (development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, titan_plugindir, '^(\w+)/.*\.a$', 'titan-plugin-%s-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, titan_plugindir, '^(\w+)/(.*/)?\.debug/.*$', 'titan-plugin-%s-dbg', '%s (debug)', recursive=True, match_path=True, prepend=True)

    titan_podir = bb.data.expand('/usr/local/share/titan/po', d)
    do_split_packages(d, titan_podir, '^(\w+)/[a-zA-Z0-9_/]+.*$', 'titan-locale-%s', '%s', recursive=True, match_path=True, prepend=True, extra_depends="titan")
}

PACKAGES_DYNAMIC = "titan-plugin-* titan-locale-*"
