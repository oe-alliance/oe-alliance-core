SUMMARY = "Openwrt DHCPv6 Client"
HOMEPAGE = "https://git.lede-project.org/?p=project/odhcp6c.git;a=summary"
LICENSE = "GPL-2.0-only"
require conf/license/license-gplv2.inc
DEPENDS = "cmake-native"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "git${GITPKGV}"

SRC_URI = "git://github.com/openwrt/odhcp6c.git;protocol=https;branch=master \
           file://ifup \
           file://ifdown \
           file://odhcp6c-update \
          "

S = "${WORKDIR}/git"

inherit pkgconfig

EXTRA_OEMAKE = " \
    'CC=${CC}' \
"

do_configure() { 
	sed -i 's#LIBUBOX 1#LIBUBOX 0#g' ${S}/CMakeLists.txt
	cmake .
} 

do_install() {
    install -d ${D}${bindir}
    install -m 755 ${S}/odhcp6c ${D}${bindir}
    install -d ${D}${sysconfdir}/network/if-up.d
    install -m 755 ${UNPACKDIR}/ifup ${D}${sysconfdir}/network/if-up.d/odhcp6c
    install -d ${D}${sysconfdir}/network/if-down.d
    install -m 755 ${UNPACKDIR}/ifdown ${D}${sysconfdir}/network/if-down.d/odhcp6c
    install -d ${D}${sbindir}
    install -m 755 ${UNPACKDIR}/odhcp6c-update ${D}${sbindir}/odhcp6c-update
}

# opkg cares shit ...
RREPLACES:${PN} += "ndisc6-rdnssd"
RCONFLICTS:${PN} += "ndisc6-rdnssd"
