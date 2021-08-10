SUMMARY = "Open Source Qt WebEngine HbbTV Browser"
PACKAGE_ARCH := "${MACHINE_ARCH}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = " \
file://LICENSE;md5=e56d5762d06c562486323805b3ee7dde \
"

DEPENDS = "qtwebengine"

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r1"

SRC_URI = "git://github.com/openhbbtvbrowser/openhbbtvbrowser.git;protocol=git \
    ${@bb.utils.contains_any("SOC_FAMILY", "hisi3716mv430 hisi3798mv200 hisi3716mv410 hisi3798mv310", "file://bg_transparent.patch", "", d)} \
    ${@bb.utils.contains_any("MACHINE_FEATURES", "qtevent1", "file://0001-use-event1.patch", "", d)} \
"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

inherit qmake5

do_install(){
    install -d ${D}${bindir}
    install -m 0755 ${B}/openhbbtvbrowser ${D}${bindir}
}

FILES_${PN} = "${bindir}"

INSANE_SKIP_${PN} += "file-rdeps"
