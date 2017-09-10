SUMMARY = "GigaBlue hubFwUpdaterCLI for ${MACHINEBUILD}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
MAINTAINER = "GigaBlue"
PACKAGE_ARCH = "${MACHINEBUILD}"

require conf/license/license-close.inc

PV = "${IMAGE_VERSION}"
PR = "r1"

S = "${WORKDIR}"

INITSCRIPT_NAME = "hubFwUpdaterCLI"
INITSCRIPT_PARAMS = "start 99 S ."

# Do not update-rc.d as update is done in postinst
# inherit update-rc.d

SRC_URI[md5sum] = "f490df8a6b25052e1951811e0f01ca0f"
SRC_URI[sha256sum] = "71c3815f12427671719822ca9959db14b8809b8c52a0d0c41ee8989cceb43d05"

SRC_URI = " \
    file://gigablue-hubfwupdatercli.zip \
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
