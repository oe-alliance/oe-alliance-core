DESCRIPTION = "Transmission is a BitTorrent client w/ a built-in Ajax-Powered Webif GUI."
SECTION = "network"
HOMEPAGE = "www.transmissionbt.com/"
LICENSE = "GPL-2.0-or-later & GPL-3.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=ba8199e739948e198310093de27175fa"

DEPENDS = "curl libevent gnutls openssl libtool intltool-native glib-2.0-native"

# Transmission release 4.0.3
SRCREV = "6b0e49bbb296f1c84785275b8a8f18b4210180af"
PV = "4.0.3"

SRC_URI = "gitsm://github.com/transmission/transmission;protocol=https;branch=main \
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
"

TARGET_CC_ARCH:remove = "-D_TIME_BITS=64"

do_install:append() {
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

FILES:${PN} = "${bindir}/transmission-daemon ${datadir}/transmission ${sysconfdir} ${localstatedir}/lib/${PN}-daemon"
FILES:${PN}-client = "${bindir}/transmission-remote ${bindir}/transmission-cli ${bindir}/transmission-create ${bindir}/transmission-show ${bindir}/transmission-edit"
FILES:${PN}-gui += "${bindir}/transmission-gtk ${datadir}/icons ${datadir}/applications ${datadir}/pixmaps"
CONFFILES:${PN} = "${sysconfdir}/default/transmission-daemon"

SYSTEMD_SERVICE:${PN} = "transmission-daemon.service"
