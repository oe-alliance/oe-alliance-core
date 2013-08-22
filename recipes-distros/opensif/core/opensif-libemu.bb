DESCRIPTION = "sifteam stb emu libraries and tools"
HOMEPAGE = "http://forum.sifteam.eu/"
SECTION = "sifteam"
DEPENDS = "sockets curl python (=2.6) swig-native libpcre libxml2 opkg"
RDEPENDS = "sockets curl python opkg libpcre libxml2"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.8-git${SRCPV}"
PKGV = "1.8-git${GITPKGV}"
PR = "r0"

INITSCRIPT_NAME = "emud"
INITSCRIPT_PARAMS = "defaults 90"

inherit update-rc.d

SRC_URI = "git://github.com/SIFTeam/libemu.git;protocol=git"

S = "${WORKDIR}/git"

SRCREV_pn-${PN} ?= "${AUTOREV}"

FILES_${PN} = "${bindir}/* ${libdir}/* ${sbindir}/* /etc/*"

CXXFLAGS_append = " -I${STAGING_INCDIR}/Sockets/ -I${STAGING_INCDIR}/python2.7/ -I${STAGING_INCDIR}/libxml2/ "

do_compile() {
	oe_runmake
	${STRIP} libemu.so
	${STRIP} _libemu.so
	${STRIP} emud
	${STRIP} emu
	${STRIP} cs
}

do_install() {
	install -d ${D}/${bindir}
	install -d ${D}/${sbindir}
	install -d ${D}/${libdir}
	install -d ${D}/etc/init.d
	install -d ${D}/${libdir}/python2.7/lib-dynload/

	install -m 644 libemu.so ${D}/${libdir}
	install -m 755 emud ${D}/${sbindir}
	install -m 755 emu ${D}/${bindir}
	install -m 755 cs ${D}/${bindir}
	install -m 755 emud.init ${D}/${sysconfdir}/init.d/emud
	install -d ${D}/etc/emud/cs
	install -d ${D}/etc/emud/emu
	install -m 755 _libemu.so ${D}/${libdir}/python2.7/lib-dynload/
	install -m 644 src/libemu.py ${D}/${libdir}/python2.7/
}
