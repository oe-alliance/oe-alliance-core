SUMMARY = "ushare is a UPnP media server"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552"

HOMEPAGE = "http://ushare.geexbox.org/"
DEPENDS = "libupnp virtual/libiconv virtual/libintl gettext"
SRC_URI = "http://ushare.geexbox.org/releases/ushare-${PV}.tar.bz2 \
        file://remove-lsb-dependency.patch \
        file://ushare.conf \
        file://init"

S = "${WORKDIR}/ushare-${PV}"

PR = "r3"

inherit autotools-brokensep update-rc.d

# the configure script is hand-crafted, it rejects some of the usual
# configure arguments
do_configure () {
    ${S}/configure \
            --prefix=${prefix} \
            --bindir=${bindir} \
            --localedir=${datadir}/locale \
            --sysconfdir=${sysconfdir} \
            --cross-compile
}

SRC_URI[md5sum] = "5bbcdbf1ff85a9710fa3d4e82ccaa251"
SRC_URI[sha256sum] = "7b9b85c79968d4f4560f02a99e33c6a33ff58f9d41d8faea79e31cce2ee78665"

INITSCRIPT_NAME = "ushare"
INITSCRIPT_PARAMS = "defaults 20"

FILES_${PN} += "${sysconfdir}/ushare.conf "

do_install_append() {
    install -D -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/ushare
    install -D -m 0644 ${WORKDIR}/ushare.conf ${D}${sysconfdir}/ushare.conf
}
