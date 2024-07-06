SUMMARY = "publish command line shell through AJAX interface"
DESCRIPTION = "Shell In A Box implements a web server that can export arbitrary command line\ntools to a web based terminal emulator. This emulator is accessible to any\nJavaScript and CSS enabled web browser and does not require any additional\nbrowser plugins."
HOMEPAGE = "https://github.com/shellinabox/shellinabox"
LICENSE = "GPL-2.0-only"
require conf/license/license-gplv2.inc
DEPENDS = "zlib"


SRCREV = "${AUTOREV}"
PV = "V2.21.0.r3+git"
PKGV = "V2.21.0.r3+git${GITPKGV}"

SRC_URI = " \
           git://github.com/oe-mirrors/shellinabox.git;protocol=https;branch=master \
           file://0001-Enforce-localhost-only.patch \
           file://0002-fix-compile-with-gcc14.patch \
           file://styles.css \
           file://shellinabox.service \
           file://shellinabox.init \
          "


inherit gitpkgv autotools-brokensep update-rc.d systemd upx-compress

EXTRA_OECONF = "--disable-runtime-loading --disable-utmp"

PACKAGECONFIG ?= "${@bb.utils.contains('DISTRO_FEATURES', 'pam', 'pam', '', d)} ssl"
PACKAGECONFIG[pam] = "--enable-pam,--disable-pam,libpam"
PACKAGECONFIG[ssl] = "--enable-ssl,--disable-ssl,openssl"

S = "${WORKDIR}/git"

INITSCRIPT_NAME = "shellinabox"
SYSTEMD_SERVICE = "shellinabox.service"

do_configure:prepend() {
	cp ${UNPACKDIR}/styles.css ${S}/shellinabox/styles.css
}

do_install:append() {
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        install -d ${D}${systemd_unitdir}/system/
        install -m 0644 ${UNPACKDIR}/shellinabox.service ${D}${systemd_unitdir}/system/
    else
        install -d ${D}${sysconfdir}/init.d
        install -m 755 ${UNPACKDIR}/shellinabox.init ${D}${sysconfdir}/init.d/shellinabox
    fi
}
