SUMMARY = "GigaBlue hubFwUpdaterCLI for ${MACHINEBUILD}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
MAINTAINER = "GigaBlue"
PACKAGE_ARCH = "${MACHINEBUILD}"

require conf/license/license-close.inc

PV = "${IMAGE_VERSION}"
PR = "r0"

S = "${WORKDIR}"

INITSCRIPT_NAME = "hubFwUpdaterCLI"
INITSCRIPT_PARAMS = "start 99 S ."

# Do not update-rc.d as update is done in postinst
# inherit update-rc.d

SRC_URI = " \
    file://hubFwUpdaterCLI.sh \
    file://hubFwUpdaterCLI \
    file://GLHubIsp.ini \
    file://FW9303.rom \
"

FILES_${PN} = "/usr/bin/hubFwUpdaterCLI /etc/init.d"

INSANE_SKIP_${PN} = "already-stripped"

do_install() {
    install -d ${D}/usr/bin/hubFwUpdaterCLI
    install -d ${D}/${sysconfdir}/init.d
    install -m 0755 ${S}/hubFwUpdaterCLI.sh ${D}/${sysconfdir}/init.d/hubFwUpdaterCLI
    install -m 0755 ${S}/hubFwUpdaterCLI ${D}/usr/bin/hubFwUpdaterCLI/hubFwUpdaterCLI
    install -m 0755 ${S}/GLHubIsp.ini ${D}/usr/bin/hubFwUpdaterCLI/GLHubIsp.ini
    install -m 0755 ${S}/FW9303.rom ${D}/usr/bin/hubFwUpdaterCLI/FW9303.rom
}

pkg_postinst_${PN}_append() {
    if test -z "$D"
    then
        # force update without requiring reboot
        /etc/init.d/hubFwUpdaterCLI version
        /etc/init.d/hubFwUpdaterCLI isp
    fi
    true
}

do_package_qa[noexec] = "1"
