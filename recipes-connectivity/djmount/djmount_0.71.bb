DESCRIPTION = "mount UPnP server content as a linux filesystem"
HOMEPAGE = "http://djmount.sourceforge.net/"
LICENSE = "GPLv2+"
DEPENDS = "libupnp fuse"
RDEPENDS_${PN} = "fuse"
PR = "r4"

LIC_FILES_CHKSUM = "file://COPYING;md5=eb723b61539feef013de476e68b5c50a"

INITSCRIPT_NAME = "djmount"
INITSCRIPT_PARAMS = "defaults"

inherit autotools update-rc.d

EXTRA_OECONF = "--with-external-libupnp --with-fuse-prefix='${STAGING_LIBDIR}'"

SRC_URI = "${SOURCEFORGE_MIRROR}/djmount/djmount-0.71.tar.gz \
	file://init \
	file://configure.ac.patch \
	file://rt_bool_arg_enable.m4.patch \
	"

SRC_URI[md5sum] = "c922753e706c194bf82a8b6ca77e6a9a"
SRC_URI[sha256sum] = "aa5bb482af4cbd42695a7e396043d47b53d075ac2f6aa18a8f8e11383c030e4f"

do_configure_prepend() {
	cp ${STAGING_DATADIR_NATIVE}/gettext/config.rpath ${S}/libupnp/config.aux/config.rpath
}

do_install_append() {
	install -d ${D}/etc/init.d
	install -m 0755 ${WORKDIR}/init ${D}/etc/init.d/djmount
}
