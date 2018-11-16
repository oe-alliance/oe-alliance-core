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
    "

DEPENDS_append_sh4 = " \
	libmme-image \
    "

RDEPENDS_${PN} = " \
    glibc-gconv-iso8859-15 \
    hotplug-e2-helper \
gawk \
bash \
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

FILES_${PN} = "oealliance/* /usr/local/bin /usr/local/bin /sbin /usr/local/bin /etc /etc/titan.restore /etc/init.d /etc/mdev /etc/network /etc/titan.restore/mnt/enigma2 /etc/titan.restore/mnt/config /etc/titan.restore/mnt/settings /etc/titan.restore/mnt/network /etc/titan.restore/var/etc/ipkg /etc/titan.restore/var/etc/tuxbox /etc/titan.restore/var/etc/titan /etc/titan.restore/var/etc/boot /etc/titan.restore/var/etc/autostart /etc/titan.restore/var/etc/network /etc/titan.restore/var/etc/ipkg /var/etc/codepages /var/etc"

do_install() {
    install -d ${D}/usr/local/bin
    install -m 0755 titan/titan ${D}/usr/local/bin/titan

    cp -r oealliance/* ${D}
}
do_install[vardepsexclude] += "DATETIME"


