DESCRIPTION = "Local Telerising TV server and Enigma2 channel import"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://../LICENSE.txt;md5=1ebbd3e34237af26da5dc08a4e440464"
require conf/python/python3-compileall.inc

inherit gittag setuptools3-openplugins

S = "${UNPACKDIR}/${BP}/src"
SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"
SRC_URI = "git://github.com/oe-alliance-plugins/Telerising.git;protocol=https;branch=main"

PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS:${PN} = " \
    enigma2-plugin-systemplugins-serviceapp \
    enigma2-plugin-extensions-epgimport \
    python3-compression \
    python3-crypt \
    python3-datetime \
    python3-fcntl \
    python3-html \
    python3-io \
    python3-json \
    python3-misc \
    python3-netclient \
    python3-shell \
    python3-threading \
    python3-twisted-core \
    python3-twisted-web \
    ca-certificates \
    iproute2-ip \
    update-rc.d \
"

do_install:append() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${S}/../data/telerising ${D}${sysconfdir}/init.d/telerising
}

FILES:${PN} += "${sysconfdir}/init.d/telerising"

pkg_prerm:${PN}() {
    if [ -z "$D" ] && [ -x /etc/init.d/telerising ]; then
        /etc/init.d/telerising stop || exit 1
    fi
}

pkg_postrm:${PN}() {
    if [ -z "$D" ] && [ ! -e /etc/init.d/telerising ]; then
        update-rc.d -f telerising remove
    fi
}
