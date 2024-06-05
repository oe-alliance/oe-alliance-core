DESCRIPTION = "Transmission is a BitTorrent client w/ a built-in Ajax-Powered Webif GUI."
SECTION = "network"
HOMEPAGE = "www.transmissionbt.com/"
LICENSE = "GPL-2.0-or-later & GPL-3.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=ba8199e739948e198310093de27175fa"

DEPENDS = "curl libevent gnutls openssl libtool intltool-native glib-2.0-native"

# Transmission release 4.0.6
SRCREV = "38c164933e9f77c110b48fe745861c3b98e3d83e"
PV = "4.0.6"
PR = "1"

SRC_URI = "gitsm://github.com/transmission/transmission;protocol=https;branch=4.0.x \
        file://0001-build-with-latomic-on-platforms-that-need-it.patch \
        file://init \
        file://service \
        file://config \
"

INITSCRIPT_NAME = "transmission-daemon"
INITSCRIPT_PARAMS = "defaults 60 "

S = "${WORKDIR}/git"

inherit cmake gettext pkgconfig update-rc.d systemd

PACKAGECONFIG = "${@bb.utils.contains('DISTRO_FEATURES','systemd','systemd','',d)}"

PACKAGECONFIG[systemd] = "-DWITH_SYSTEMD=ON,-DWITH_SYSTEMD=OFF,systemd,"

EXTRA_OECMAKE += " \
    -DENABLE-GTK=OFF \
    -DENABLE_MAC=OFF \
    -DENABLE_TESTS=OFF \
    -DENABLE_CLI=ON \
"

do_install:append() {
    install -d ${D}${sysconfdir}/default
    install -m 0755 ${UNPACKDIR}/config ${D}${sysconfdir}/default/transmission-daemon

    if ${@bb.utils.contains('DISTRO_FEATURES', 'sysvinit', 'true', 'false', d)}; then
        install -d ${D}${sysconfdir}/init.d
        install -m 0755 ${UNPACKDIR}/init ${D}${sysconfdir}/init.d/transmission-daemon
        install -d ${D}${localstatedir}/lib/transmission-daemon
    fi

    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_unitdir}/system
        install -m 0644 ${UNPACKDIR}/service ${D}${systemd_unitdir}/system/transmission-daemon.service
    fi
}

PACKAGES += "${PN}-gui ${PN}-client"

FILES:${PN} = "${bindir}/transmission-daemon ${datadir}/transmission ${sysconfdir} ${localstatedir}/lib/${PN}-daemon"
FILES:${PN}-client = "${bindir}/transmission-remote ${bindir}/transmission-cli ${bindir}/transmission-create ${bindir}/transmission-show ${bindir}/transmission-edit"
FILES:${PN}-gui += "${bindir}/transmission-gtk ${datadir}/icons ${datadir}/applications ${datadir}/pixmaps"
CONFFILES:${PN} = "${sysconfdir}/default/transmission-daemon"

SYSTEMD_SERVICE:${PN} = "transmission-daemon.service"
