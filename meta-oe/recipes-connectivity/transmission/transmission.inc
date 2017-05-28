DESCRIPTION = "Transmission is a BitTorrent client w/ a built-in Ajax-Powered Webif GUI."
SECTION = "network"
HOMEPAGE = "www.transmissionbt.com/"
DEPENDS = "gnutls openssl gettext libtool intltool-native curl glib-2.0-native libevent librtmp"
LICENSE = "GPLv2 & GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=0dd9fcdc1416ff123c41c785192a1895"

SRC_URI = "https://github.com/transmission/transmission/archive/${PV}.tar.gz;name=archive \
           file://init \
           file://config"

INITSCRIPT_NAME = "transmission-daemon"
INITSCRIPT_PARAMS = "defaults 60 "

inherit autotools update-rc.d gettext

B = "${S}"
do_configure_prepend() {
	sed -i /AM_GLIB_GNU_GETTEXT/d ${S}/configure.ac
	cd ${S}
	./update-version-h.sh
	intltoolize --copy --force --automake
}

do_install_append() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/transmission-daemon
    install -d ${D}${sysconfdir}/default
    install -m 0755 ${WORKDIR}/config ${D}${sysconfdir}/default/transmission-daemon
    install -d ${D}${localstatedir}/lib/transmission-daemon
}

PACKAGES += "${PN}-gui ${PN}-client"

FILES_${PN} = "${bindir}/transmission-daemon ${datadir}/transmission ${sysconfdir} ${localstatedir}/lib/${PN}-daemon"
FILES_${PN}-client = "${bindir}/transmission-remote ${bindir}/transmission-cli ${bindir}/transmission-create ${bindir}/transmission-show ${bindir}/transmission-edit"
FILES_${PN}-gui += "${bindir}/transmission-gtk ${datadir}/icons ${datadir}/applications ${datadir}/pixmaps"
CONFFILES_${PN} = "${sysconfdir}/default/transmission-daemon"

