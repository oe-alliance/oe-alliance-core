DESCRIPTION = "Transmission is a BitTorrent client w/ a built-in Ajax-Powered Webif GUI."
SECTION = "network"
HOMEPAGE = "www.transmissionbt.com/"
LICENSE = "GPLv2 & GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=0dd9fcdc1416ff123c41c785192a1895"

DEPENDS = "gnutls openssl gettext libtool intltool-native curl glib-2.0-native libevent librtmp"

SRCREV = "d8e60ee44f4295935bd98bf741f85ed19f5a7dfb"

SRC_URI = "git://github.com/transmission/transmission.git;name=archive \
        file://configure-kill-intl-check.patch \
        file://allow_the_rpc_server_to_listen_on_an_ipv6_address.patch \
        file://configure-allow-local-network.patch \
        file://init \
        file://config\
"

INITSCRIPT_NAME = "transmission-daemon"
INITSCRIPT_PARAMS = "defaults 60 "

# add --disable-nls to configure options this way because of def in gettext.class
USE_NLS = "no"

EXTRA_OECONF = "\
    --without-gtk \
    --disable-cli \
    --disable-mac \
    --enable-lightweight \
    --enable-daemon \
    CPPFLAGS=-DTR_EMBEDDED \
"

inherit autotools update-rc.d gettext

S = "${WORKDIR}/git"
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
