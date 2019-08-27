require conf/license/license-gplv2.inc
require softcam.inc
inherit cmake

SUMMARY = "OScam ${PV} Open Source Softcam"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

FILESEXTRAPATHS_prepend := "${THISDIR}/enigma2-plugin-softcams-oscam:"

SRCREV = "${AUTOREV}"
PV = "svn${SRCPV}"
SRC_URI = "svn://www.streamboard.tv/svn/oscam;protocol=http;module=trunk;scmdata=keep"

PR = "r1"

DEPENDS = "libusb openssl"
RCONFLICTS_${PN} = "oscam oscam-stable oscam-unstable oscam-experimental oscam-util-list-smargo"
RCONFLICTS_${PN} += "enigma2-plugin-softcams-oscam-cs"
RCONFLICTS_${PN} += "enigma2-plugin-softcams-oscam-stable-cs enigma2-plugin-softcams-oscam-unstable-cs enigma2-plugin-softcams-oscam-experimental-cs"
RCONFLICTS_${PN} += "enigma2-plugin-softcams-oscam-stable    enigma2-plugin-softcams-oscam-unstable    enigma2-plugin-softcams-oscam-experimental"
RREPLACES_${PN} = "${RCONFLICTS_${PN}}"

S = "${WORKDIR}/trunk"
CAMNAME = "oscam"
CAMSTART = "/usr/bin/oscam --config-dir /etc/tuxbox/config/oscam --daemon --pidfile /tmp/oscam.pid --restart 2 --utf8"
CAMSTOP = "kill \`cat /tmp/oscam.pid\` 2> /dev/null"

SRC_URI += " \
    file://oscam.conf \
    file://oscam.server \
    file://oscam.srvid \
    file://oscam.user \
    file://oscam.provid"

CONFFILES = "/etc/tuxbox/config/oscam/oscam.conf /etc/tuxbox/config/oscam/oscam.server /etc/tuxbox/config/oscam/oscam.srvid /etc/tuxbox/config/oscam/oscam.user /etc/tuxbox/config/oscam/oscam.provid"

FILES_${PN} = "/usr/bin/oscam /etc/tuxbox/config/oscam/* /etc/init.d/softcam.oscam"

EXTRA_OECMAKE += "\
    -DOSCAM_SYSTEM_NAME=Tuxbox \
    -DWEBIF=1 \
    -DWITH_STAPI=0 \
    -DHAVE_LIBUSB=1 \
    -DSTATIC_LIBUSB=1 \
    -DWITH_SSL=1 \
    -DHAVE_PCSC=0"

do_install() {
    install -d ${D}/etc/tuxbox/config/oscam
    install -m 0644 ${WORKDIR}/oscam.* ${D}/etc/tuxbox/config/oscam/
    install -d ${D}/usr/bin
    install -m 0755 ${B}/oscam ${D}/usr/bin
}

INSANE_SKIP_${PN} = "already-stripped"
