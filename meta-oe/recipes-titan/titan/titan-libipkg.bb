SUMMARY = "Itsy Package Management System library"
SECTION = "base"
LICENSE = "GPLv2"
require conf/license/license-gplv2.inc

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit autotools pkgconfig

SRCREV = "${AUTOREV}"
PV = "${@bb.fetch2.get_srcrev(d)}"

SRC_URI = "svn://public:public@sbnc.dyndns.tv/svn/titan;module=libipkg;protocol=http"

DEPENDS = "libarchive"
RDEPENDS:${PN} = "libarchive"

S = "${WORKDIR}/libipkg"

do_configure() {
    cd ${S}

	libtoolize --force
	aclocal -I ${STAGING_DIR_TARGET}/usr/share/aclocal
	autoconf
	automake --foreign --add-missing
	./configure --host=${HOST_SYS} --build=${BUILD_SYS}
}

do_compile() {
	cd ${S}
	make clean
	make -f Makefile
#	${STRIP} ${S}/.libs/*.so
}

FILES:${PN} = "/usr/bin"
FILES:${PN} += "/usr/lib"

do_install() {
    install -d ${D}${bindir}
    install -d ${D}${libdir}
    install -m 0755 ${S}/.libs/ipkg-cl ${D}${bindir}
    install -m 0755 ${S}/.libs/libipkg.so.0.0.0 ${D}${libdir}/
    ln -s libipkg.so.0.0.0 ${D}${libdir}/libipkg.so
    ln -s libipkg.so.0.0.0 ${D}${libdir}/libeipkg.so.0
}

