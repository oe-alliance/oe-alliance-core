SUMMARY = "ALSA sound utilities"
HOMEPAGE = "http://www.alsa-project.org"
BUGTRACKER = "https://bugtrack.alsa-project.org/alsa-bug/login_page.php"
SECTION = "console/utils"
LICENSE = "GPLv2+"
require conf/license/license-gplv2.inc
RDEPENDS_${PN} = "alsa-utils-amixer"

SRC_URI = " file://alsa-mixer-conf"

do_install() {
    install -d ${D}/${sysconfdir}/init.d
    install -d ${D}/${sysconfdir}/rcS.d
    install -m 0755 ${WORKDIR}/alsa-mixer-conf ${D}${sysconfdir}/init.d
    ln -sf ../init.d/alsa-mixer-conf ${D}${sysconfdir}/rcS.d/S40alsa-mixer-conf
}