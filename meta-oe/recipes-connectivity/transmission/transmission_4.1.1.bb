DESCRIPTION = "Transmission is a BitTorrent client w/ a built-in Ajax-Powered Webif GUI."
SECTION = "network"
HOMEPAGE = "www.transmissionbt.com/"
LICENSE = "GPL-2.0-or-later & GPL-3.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=d54f298b276b8cc5f20168e43a0e8103"

DEPENDS = "curl libevent gnutls openssl libtool intltool-native glib-2.0-native"


SRCREV = "e51d77e202206ee1389cbd6d9ab154421942b530"

SRC_URI = "gitsm://github.com/transmission/transmission;protocol=https;branch=4.1.x \
        file://init \
        file://service \
        file://config \
"

INITSCRIPT_NAME = "transmission-daemon"
INITSCRIPT_PARAMS = "defaults 60 "

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

INSANE_SKIP = "32bit-time"
