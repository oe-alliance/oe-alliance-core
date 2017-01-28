SUMMARY = "Start, stop and select softcams."
MAINTAINER = "PLi team"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

PACKAGES = "${PN}"

PV = "1.0"
PR = "r1"

INITSCRIPT_NAME = "softcam"
INITSCRIPT_PARAMS = "defaults 50"
inherit update-rc.d

FILES_${PN} = "/etc"

do_install() {
    install -d ${D}/etc/init.d
    install -m 755 ${S}/softcam.None ${D}/etc/init.d/softcam.None
    ln -s softcam.None ${D}/etc/init.d/softcam
}

do_compile_append() {
    echo "# Placeholder for no cam" > ${S}/softcam.None
}
