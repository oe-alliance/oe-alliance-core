SUMMARY = "GigaBlueHD Enigma2 Skin "
MAINTAINER = "oerlgrey"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
inherit allarch

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "${IMAGE_VERSION}+git${SRCPV}"
PKGV = "${IMAGE_VERSION}+git${GITPKGV}"
VER ="${IMAGE_VERSION}"
PR = "r1"

SRC_URI="git://github.com/oerlgrey/TeamBlueHD.git;protocol=https"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_install() {
    cp -rp ${S}/usr ${D}/
}

pkg_prerm_${PN} () {
#!/bin/sh
if [ -f /usr/lib/enigma2/python/Components/Converter/TeamBlueHDClockToText.pyo ]; then
    rm /usr/lib/enigma2/python/Components/Converter/TeamBlueHDClockToText.pyo
fi
if [ -f /usr/lib/enigma2/python/Components/Converter/TeamBlueHDDiskSpace.pyo ]; then
    rm /usr/lib/enigma2/python/Components/Converter/TeamBlueHDDiskSpace.pyo
fi
if [ -f /usr/lib/enigma2/python/Components/Converter/TeamBlueHDDolbyState.pyo ]; then
    rm /usr/lib/enigma2/python/Components/Converter/TeamBlueHDDolbyState.pyo
fi
if [ -f /usr/lib/enigma2/python/Components/Converter/TeamBlueHDECMLine.pyo ]; then
    rm /usr/lib/enigma2/python/Components/Converter/TeamBlueHDECMLine.pyo
fi
if [ -f /usr/lib/enigma2/python/Components/Converter/TeamBlueHDEventName.pyo ]; then
    rm /usr/lib/enigma2/python/Components/Converter/TeamBlueHDEventName.pyo
fi
if [ -f /usr/lib/enigma2/python/Components/Converter/TeamBlueHDEventTime.pyo ]; then
    rm /usr/lib/enigma2/python/Components/Converter/TeamBlueHDEventTime.pyo
fi
if [ -f /usr/lib/enigma2/python/Components/Converter/TeamBlueHDFrontendInfo.pyo ]; then
    rm /usr/lib/enigma2/python/Components/Converter/TeamBlueHDFrontendInfo.pyo
fi
if [ -f /usr/lib/enigma2/python/Components/Converter/TeamBlueHDMenuIconPath.pyo ]; then
    rm /usr/lib/enigma2/python/Components/Converter/TeamBlueHDMenuIconPath.pyo
fi
if [ -f /usr/lib/enigma2/python/Components/Converter/TeamBlueHDMovieReference.pyo ]; then
    rm /usr/lib/enigma2/python/Components/Converter/TeamBlueHDMovieReference.pyo
fi
if [ -f /usr/lib/enigma2/python/Components/Converter/TeamBlueHDRemainingToText.pyo ]; then
    rm /usr/lib/enigma2/python/Components/Converter/TeamBlueHDRemainingToText.pyo
fi
if [ -f /usr/lib/enigma2/python/Components/Converter/TeamBlueHDServiceEndTime.pyo ]; then
    rm /usr/lib/enigma2/python/Components/Converter/TeamBlueHDServiceEndTime.pyo
fi
if [ -f /usr/lib/enigma2/python/Components/Converter/TeamBlueHDServiceInfoEX.pyo ]; then
    rm /usr/lib/enigma2/python/Components/Converter/TeamBlueHDServiceInfoEX.pyo
fi
if [ -f /usr/lib/enigma2/python/Components/Converter/TeamBlueHDServiceName2.pyo ]; then
    rm /usr/lib/enigma2/python/Components/Converter/TeamBlueHDServiceName2.pyo
fi
if [ -f /usr/lib/enigma2/python/Components/Renderer/TeamBlueHDDolbyIcon.pyo ]; then
    rm /usr/lib/enigma2/python/Components/Renderer/TeamBlueHDDolbyIcon.pyo
fi
if [ -f /usr/lib/enigma2/python/Components/Renderer/TeamBlueHDEmptyEpg.pyo ]; then
    rm /usr/lib/enigma2/python/Components/Renderer/TeamBlueHDEmptyEpg.pyo
fi
if [ -f /usr/lib/enigma2/python/Components/Renderer/TeamBlueHDEmptyEpg3.pyo ]; then
    rm /usr/lib/enigma2/python/Components/Renderer/TeamBlueHDEmptyEpg3.pyo
fi
if [ -f /usr/lib/enigma2/python/Components/Renderer/TeamBlueHDPixmapScaler.pyo ]; then
    rm /usr/lib/enigma2/python/Components/Renderer/TeamBlueHDPixmapScaler.pyo
fi
if [ -f /usr/lib/enigma2/python/Components/Renderer/TeamBlueHDRunningText.pyo ]; then
    rm /usr/lib/enigma2/python/Components/Renderer/TeamBlueHDRunningText.pyo
fi
if [ -f /usr/lib/enigma2/python/Components/Renderer/TeamBlueHDServiceIcon.pyo ]; then
    rm /usr/lib/enigma2/python/Components/Renderer/TeamBlueHDServiceIcon.pyo
fi
if [ -f /usr/lib/enigma2/python/Components/Renderer/TeamBlueHDSingleEpgList.pyo ]; then
    rm /usr/lib/enigma2/python/Components/Renderer/TeamBlueHDSingleEpgList.pyo
fi
exit 0
}

do_package_qa[noexec] = "1"
