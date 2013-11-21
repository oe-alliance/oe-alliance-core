DESCRIPTION = "GraphLCD is a project to support graphical LC displays connected to the PC."
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://graphlcd.conf;md5=56e412a181c090acd15e5153ae40f869"

include conf/license/license-gplv2.inc

DEPENDS = "virtual/libusb0 ncurses readline fontconfig freetype"

inherit gitpkgv

PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r9"

SRCREV = "AUTOINC"

EXTRA_OECONF = " \
	"

SRC_URI = "\
	git://projects.vdr-developer.org/graphlcd-base.git;branch=touchcol;protocol=git \
	file://graphlcd.conf \
	file://glcd-mipsel.patch \
	file://glcd-vuduo2.patch \
	"

S =  "${WORKDIR}/git"

inherit autotools pkgconfig

PACKAGES =+ "${PN}-tools ${PN}-skin"

FILES_${PN}-tools = "\
	/usr/bin/* \
	"
FILES_${PN}-skin = "\
	/usr/lib/libglcdskin*.so.* \
	"

#INITSCRIPT_NAME = "graphlcd"
CONFFILES_${PN} += "${sysconfdir}/graphlcd.conf"

do_configure_prepend() {
	sed -i s/'CXXFLAGS = '/'#CXXFLAGS = '/g ${S}/Make.config
	sed -i s/'HAVE_FONTCONFIG=1'/'#HAVE_FONTCONFIG=1'/g ${S}/Make.config
}

do_install_append() {
	install -d ${D}${sysconfdir}
	install -m 0600 ${WORKDIR}/graphlcd.conf ${D}${sysconfdir}/graphlcd.conf
}

