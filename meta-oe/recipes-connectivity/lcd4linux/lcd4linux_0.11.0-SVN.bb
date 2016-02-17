SUMMARY = "LCD4Linux is a small program that grabs information from the kernel and some subsystems and displays it on an external liquid crystal display."
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

DEPENDS = "virtual/libusb0 ncurses readline jpeg dbus-glib sqlite3"
RDEPENDS_${PN} = "jpeg"

PV = "0.11.0-SVN"
PR = "r2"

EXTRA_OECONF = " \
    --with-glib-prefix=${STAGING_LIBDIR}/.. \
    --with-glib-exec-prefix=${STAGING_LIBDIR}/.. \
    --with-ncurses=${STAGING_LIBDIR}/..\
    --without-x\
"

SRC_URI = "svn://ssl.bulix.org/svn/lcd4linux/;module=trunk;protocol=https;rev=1200 \
    file://lcd4linux.init"

S =  "${WORKDIR}/trunk"

addtask setlibtool before do_configure after do_patch

do_setlibtool() {
    sed -i "s#LIBTOOL=libtool#LIBTOOL=\${STAGING_BINDIR_CROSS}\/\${HOST_SYS}-libtool#" ${S}/Makefile.am
}

do_setlibtool_arm (){
    sed -i "s#LIBTOOL=libtool#LIBTOOL=\${STAGING_BINDIR_CROSS}\/arm-oe-linux-gnueabi-libtool#" ${S}/Makefile.am
}

INITSCRIPT_PARAMS_vuplus = "stop 10 0 6 ."
inherit autotools update-rc.d gettext pkgconfig

INITSCRIPT_NAME = "lcd4linux"
CONFFILES_${PN} += "${sysconfdir}/lcd4linux.conf"

do_install_append() {
    install -d ${D}/${sysconfdir}
    install -m 0600 ${S}/lcd4linux.conf.sample  ${D}/${sysconfdir}/lcd4linux.conf
    install -d ${D}/${INIT_D_DIR}
    install -m 0755 ${WORKDIR}/lcd4linux.init ${D}/${INIT_D_DIR}/lcd4linux
}

