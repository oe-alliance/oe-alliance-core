SUMMARY = "SysV init scripts for Amlogic framebuffer set-up"
DESCRIPTION = "Provides basic set-up for the amlogic framebuffer"
SECTION = "base"

include conf/license/license-gplv2.inc

PR = "r7"

INHIBIT_DEFAULT_DEPS = "1"
RDEPENDS_${PN} = "initscripts fbset fbset-modes"

S = "${WORKDIR}"

SRC_URI = " \
    file://amlsetfb.sh \
    file://aaa.sh \
"

COMPATIBLE_MACHINE = "(wetekplay|wetekplay2|odroidc2)"

do_install() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/amlsetfb.sh  ${D}${sysconfdir}/init.d/amlsetfb.sh
    install -m 0755 ${WORKDIR}/aaa.sh  ${D}${sysconfdir}/init.d/aaa.sh
    update-rc.d -r ${D} amlsetfb.sh start 03 S .
    update-rc.d -r ${D} aaa.sh start 0 S .
}
