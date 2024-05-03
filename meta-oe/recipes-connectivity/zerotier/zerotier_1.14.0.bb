SUMMARY = "ZeroTier is a smart programmable Ethernet switch for planet Earth"
MAINTAINER = "https://github.com/zerotier/ZeroTierOne"
DESCRIPTION = "Allows all networked devices, VMs, containers, and applications to communicate \
            as if they all reside in the same physical data center or cloud region."
HOMEPAGE = "https://www.zerotier.com"
SECTION = "net"
LICENSE = "GPL-3.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=11bbae9cacaf61dd7fc10035f6f5c68e"

DEPENDS = "miniupnpd"
RRECOMMENDS:${PN} = "kernel-module-tun"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.14.0+git"
PKGV = "1.14.0+git${GITPKGV}"

SRC_URI = "git://github.com/zerotier/ZeroTierOne.git;protocol=https;branch=main \
        file://zerotier \
        file://0001-accept-external-ldflags.patch \
        file://0003-dont-enable-AES-hwcaps-for-arm.patch \
"

S = "${WORKDIR}/git"

INITSCRIPT_NAME = "zerotier"

inherit autotools-brokensep update-rc.d systemd

EXTRA_OEMAKE = " ZT_SSO_SUPPORTED=0 "

do_compile:prepend:mipsel() {
    export LDLIBS+=-latomic
}

do_install:append() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/zerotier ${D}${sysconfdir}/init.d/zerotier
}

INSANE_SKIP:${PN} = "already-stripped ldflags"
