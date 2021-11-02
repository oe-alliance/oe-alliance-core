DESCRIPTION = "streamlinksrv is a helper deamon for streamlink"
SECTION = "devel/python"
MAINTAINER = "SatDreamGR Billy2011"
HOMEPAGE = "www.satdreamgr.com"
LICENSE = "GPLv2+"
require conf/license/license-gplv2.inc

inherit allarch

RDEPENDS_${PN} = "python-core streamlink-27"

SRC_URI = "git://github.com/athoik/livestreamersrv.git;protocol=https"
S = "${WORKDIR}/git"

inherit gittag
SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "${GITPKGVTAG}"

PACKAGES = "${PN}"

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
