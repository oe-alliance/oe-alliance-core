SUMMARY = "publish command line shell through AJAX interface"
DESCRIPTION = "Shell In A Box implements a web server that can export arbitrary command line\ntools to a web based terminal emulator. This emulator is accessible to any\nJavaScript and CSS enabled web browser and does not require any additional\nbrowser plugins."
HOMEPAGE = "https://github.com/shellinabox/shellinabox"
LICENSE = "GPLv2"
require conf/license/license-gplv2.inc
DEPENDS = "zlib"


SRCREV = "5c7fb5cde2d2a74775af040549bb5cb11aae6790"
PV = "2.20+git${SRCPV}"

SRC_URI = " \
           git://github.com/shellinabox/shellinabox.git \
           file://0001-Use-IPv6.patch \
           file://0002-Enforce-localhost-only.patch \
           file://styles.css \
           file://shellinabox.service \
           file://shellinabox.init \
          "


inherit autotools-brokensep update-rc.d systemd

EXTRA_OECONF = "--disable-runtime-loading --disable-utmp"

PACKAGECONFIG ?= "${@bb.utils.contains('DISTRO_FEATURES', 'pam', 'pam', '', d)} ssl"
PACKAGECONFIG[pam] = "--enable-pam,--disable-pam,libpam"
PACKAGECONFIG[ssl] = "--enable-ssl,--disable-ssl,openssl"

S = "${WORKDIR}/git"

INITSCRIPT_NAME = "shellinabox"
SYSTEMD_SERVICE = "shellinabox.service"

do_configure_prepend() {
	cp ${WORKDIR}/styles.css ${S}/shellinabox/styles.css
}

do_install_append() {
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        install -d ${D}${systemd_unitdir}/system/
        install -m 0644 ${WORKDIR}/shellinabox.service ${D}${systemd_unitdir}/system/
    else
        install -d ${D}${sysconfdir}/init.d
        install -m 755 ${WORKDIR}/shellinabox.init ${D}${sysconfdir}/init.d/shellinabox
    fi
}
