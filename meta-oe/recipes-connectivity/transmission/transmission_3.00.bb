DESCRIPTION = "Transmission is a BitTorrent client w/ a built-in Ajax-Powered Webif GUI."
SECTION = "network"
HOMEPAGE = "www.transmissionbt.com/"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=73f535ddffcf2a0d3af4f381f84f9b33"

DEPENDS = "curl libevent gnutls openssl libtool intltool-native glib-2.0-native"

# Transmission release 3.00
SRCREV = "bb6b5a062ee594dfd4b7a12a6b6e860c43849bfd"
PV = "3.00"

SRC_URI = "gitsm://github.com/transmission/transmission;protocol=https \
        file://configure-kill-intl-check.patch \
        file://configure-allow-local-network.patch \
        file://init \
        file://service \
        file://config \
"

INITSCRIPT_NAME = "transmission-daemon"
INITSCRIPT_PARAMS = "defaults 60 "

S = "${WORKDIR}/git"

inherit autotools-brokensep gettext update-rc.d systemd

PACKAGECONFIG = "${@bb.utils.contains('DISTRO_FEATURES','systemd','systemd','',d)}"

PACKAGECONFIG[systemd] = "--with-systemd,--without-systemd,systemd,"

EXTRA_OECONF += " \
    --without-gtk \
    --disable-cli \
    --disable-mac \
    --enable-lightweight \
    --enable-daemon \
    CPPFLAGS=-DTR_EMBEDDED \
"

do_configure_prepend() {
    sed -i /AM_GLIB_GNU_GETTEXT/d ${S}/configure.ac
    cd ${S}
    ./update-version-h.sh
    intltoolize --copy --force --automake
}

do_install_append() {
    install -d ${D}${sysconfdir}/default
    install -m 0755 ${WORKDIR}/config ${D}${sysconfdir}/default/transmission-daemon

    if ${@bb.utils.contains('DISTRO_FEATURES', 'sysvinit', 'true', 'false', d)}; then
        install -d ${D}${sysconfdir}/init.d
        install -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/transmission-daemon
        install -d ${D}${localstatedir}/lib/transmission-daemon
    fi

    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_unitdir}/system
        install -m 0644 ${WORKDIR}/service ${D}${systemd_unitdir}/system/transmission-daemon.service
    fi
}

PACKAGES += "${PN}-gui ${PN}-client"

FILES_${PN} = "${bindir}/transmission-daemon ${datadir}/transmission ${sysconfdir} ${localstatedir}/lib/${PN}-daemon"
FILES_${PN}-client = "${bindir}/transmission-remote ${bindir}/transmission-cli ${bindir}/transmission-create ${bindir}/transmission-show ${bindir}/transmission-edit"
FILES_${PN}-gui += "${bindir}/transmission-gtk ${datadir}/icons ${datadir}/applications ${datadir}/pixmaps"
CONFFILES_${PN} = "${sysconfdir}/default/transmission-daemon"

SYSTEMD_SERVICE_${PN} = "transmission-daemon.service"
