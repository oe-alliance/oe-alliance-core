DESCRIPTION = "streamlinksrv is a helper deamon for streamlink"
SECTION = "devel/python"
MAINTAINER = "SatDreamGR Billy2011"
HOMEPAGE = "www.satdreamgr.com"
LICENSE = "GPLv2+"
require conf/license/license-gplv2.inc

inherit allarch

RDEPENDS_${PN} = "${PYTHON_PN}-core streamlink"

inherit ${PYTHON_PN}-dir gitpkgv

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/oe-mirrors/livestreamersrv;protocol=https;branch=streamlinksrv"

S = "${WORKDIR}/git"

do_install_append() {
    install -d ${D}${sbindir}
    install -d ${D}${datadir}
    install -d ${D}${sysconfdir}/init.d
    install -d ${D}${sysconfdir}/rc3.d
    install -d ${D}${sysconfdir}/rc4.d
    install -m 0755 ${S}/streamlinksrv ${D}${sbindir}
    install -m 0644 ${S}/offline.mp4 ${D}${datadir}
    ln -sf ${sbindir}/streamlinksrv ${D}${sysconfdir}/init.d/streamlinksrv
    ln -sf ../init.d/streamlinksrv ${D}${sysconfdir}/rc3.d/S50streamlinksrv
    ln -sf ../init.d/streamlinksrv ${D}${sysconfdir}/rc4.d/S50streamlinksrv
}

FILES_${PN} = "/"

do_package_qa[noexec] = "1"
