DESCRIPTION = "OE-Alliance remote control and box image files for Enimga2 and OpenWebIF."
MAINTAINER = "OE-Alliance"
require conf/license/license-gplv2.inc
PACKAGE_ARCH = "${MACHINEBUILD}"

inherit gitpkgv

SRCREV = "${AUTOREV}"

PV = "${IMAGE_VERSION}+git${SRCPV}"
PKGV = "${IMAGE_VERSION}+git${GITPKGV}"

SRC_URI = "git://github.com/oe-alliance/remotes;protocol=https;branch=master"

S = "${WORKDIR}/git"

do_install() {
    install -d ${D}${datadir}/enigma2
    install -d ${D}${datadir}/enigma2/rc
    install -m 0644 ${S}/remotes.xml ${D}${datadir}/enigma2
    if [ ${RCNAME} != "N/A" ] ; then
        install -m 0644 ${S}/png/${RCNAME}.png ${D}${datadir}/enigma2/rc
        install -m 0644 ${S}/xml/${RCNAME}.xml ${D}${datadir}/enigma2/rc
    fi
    install -m 0644 ${S}/png/dmm1.png ${D}${datadir}/enigma2/rc
    install -m 0644 ${S}/xml/dmm1.xml ${D}${datadir}/enigma2/rc
    install -d ${D}${datadir}/enigma2/receiver
    if [ ${MACHINEBUILD} = "ventonhdx" ]; then
        install -m 0644 ${S}/boxes/uniboxhd1.png ${D}${datadir}/enigma2/receiver/uniboxhd1_front.png
        install -m 0644 ${S}/boxes/uniboxhd2.png ${D}${datadir}/enigma2/receiver/uniboxhd2_front.png
        install -m 0644 ${S}/boxes/uniboxhd3.png ${D}${datadir}/enigma2/receiver/uniboxhd3_front.png
    elif [ ${MACHINE} = "et6x00" ]; then
        install -m 0644 ${S}/boxes/et6x00.png ${D}${datadir}/enigma2/receiver/et6x00_front.png
        install -m 0644 ${S}/boxes/et6500.png ${D}${datadir}/enigma2/receiver/et6500_front.png
    elif [ ${MACHINEBUILD} = "azboxhd" ]; then
        install -m 0644 ${S}/boxes/elite.png ${D}${datadir}/enigma2/receiver/elite_front.png
        install -m 0644 ${S}/boxes/premium.png ${D}${datadir}/enigma2/receiver/premium_front.png
        install -m 0644 ${S}/boxes/premium+.png ${D}${datadir}/enigma2/receiver/premium+_front.png
        install -m 0644 ${S}/boxes/ultra.png ${D}${datadir}/enigma2/receiver/ultra_front.png
    elif [ ${MACHINEBUILD} = "xpeedlx" ]; then
        install -m 0644 ${S}/boxes/xpeedlx1.png ${D}${datadir}/enigma2/receiver/xpeedlx1_front.png
        install -m 0644 ${S}/boxes/xpeedlx2.png ${D}${datadir}/enigma2/receiver/xpeedlx2_front.png
    elif [ ${MACHINEBUILD} = "atemio6x00" ]; then
        install -m 0644 ${S}/boxes/atemio6100.png ${D}${datadir}/enigma2/receiver/atemio6100_front.png
        install -m 0644 ${S}/boxes/atemio6200.png ${D}${datadir}/enigma2/receiver/atemio6200_front.png
    elif [ ${MACHINEBUILD} = "et7x00" ]; then
        install -m 0644 ${S}/boxes/et7000.png ${D}${datadir}/enigma2/receiver/et7000_front.png
        install -m 0644 ${S}/boxes/et7100.png ${D}${datadir}/enigma2/receiver/et7100_front.png
        install -m 0644 ${S}/boxes/et7500.png ${D}${datadir}/enigma2/receiver/et7500_front.png
    elif [ ${MACHINEBUILD} = "twinboxlcd" ]; then
        install -m 0644 ${S}/boxes/twinboxlcdci.png ${D}${datadir}/enigma2/receiver/twinboxlcdci_front.png
        install -m 0644 ${S}/boxes/twinboxlcd.png ${D}${datadir}/enigma2/receiver/twinboxlcd_front.png
    elif [ ${MACHINEBUILD} = "dm520" ]; then
        install -m 0644 ${S}/boxes/dm520.png ${D}${datadir}/enigma2/receiver/dm520_front.png
        install -m 0644 ${S}/boxes/dm525.png ${D}${datadir}/enigma2/receiver/dm525_front.png
    elif [ ${MACHINEBUILD} = "dm900" ]; then
        install -m 0644 ${S}/boxes/dm900.png ${D}${datadir}/enigma2/receiver/dm900_front.png
        install -m 0644 ${S}/boxes/dm920.png ${D}${datadir}/enigma2/receiver/dm920_front.png
    elif [ ${MACHINEBUILD} = "sf8008" ]; then
        install -m 0644 ${S}/boxes/sf8008.png ${D}${datadir}/enigma2/receiver/sf8008_front.png
        install -m 0644 ${S}/boxes/sf8008s.png ${D}${datadir}/enigma2/receiver/sf8008s_front.png
        install -m 0644 ${S}/boxes/sf8008t.png ${D}${datadir}/enigma2/receiver/sf8008t_front.png.png
    elif [ ${MACHINEBUILD} = "sfx6008" ]; then
        install -m 0644 ${S}/boxes/sfx6008.png ${D}${datadir}/enigma2/receiver/sfx6008_front.png
        install -m 0644 ${S}/boxes/sfx6018.png ${D}${datadir}/enigma2/receiver/sfx6018_front.png
    elif [ ${MACHINEBUILD} = "ustym4kpro" ]; then
        install -m 0644 ${S}/boxes/ustym4kpro.png ${D}${datadir}/enigma2/receiver/ustym4kpro_front.png
        install -m 0644 ${S}/boxes/ustym4kprosingle.png ${D}${datadir}/enigma2/receiver/ustym4kprosingle_front.png
        install -m 0644 ${S}/boxes/ustym4kprotwin.png ${D}${datadir}/enigma2/receiver/ustym4kprotwin_front.png
    else
        install -m 0644 ${S}/boxes/${MACHINEBUILD}.png ${D}${datadir}/enigma2/receiver/${MACHINEBUILD}_front.png
    fi
}

FILES:${PN} = "${datadir}/enigma2 ${datadir}/enigma2/rc ${datadir}/enigma2/receiver"
