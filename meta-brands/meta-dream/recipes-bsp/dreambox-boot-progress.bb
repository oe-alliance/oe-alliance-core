SUMMARY = "Display progress bar while booting"
SECTION = "base"
PRIORITY = "optional"
DEPENDS = ""
PV = "3"
PR = "r0"

require conf/license/license-gplv2.inc

SRC_URI = "file://progress"

do_install () {
#
# Create directories and install device independent scripts
#
    install -d ${D}${sysconfdir}/init.d
    install -d ${D}${sysconfdir}/rcS.d
    install -m 0755 ${WORKDIR}/progress ${D}${sysconfdir}/init.d
#
# Create runlevel links
#
    ln -sf ../init.d/progress ${D}${sysconfdir}/rcS.d/S06progress
    ln -sf ../init.d/progress ${D}${sysconfdir}/rcS.d/S10progress
    ln -sf ../init.d/progress ${D}${sysconfdir}/rcS.d/S20progress
    ln -sf ../init.d/progress ${D}${sysconfdir}/rcS.d/S30progress
    ln -sf ../init.d/progress ${D}${sysconfdir}/rcS.d/S40progress
    ln -sf ../init.d/progress ${D}${sysconfdir}/rcS.d/S50progress
}

