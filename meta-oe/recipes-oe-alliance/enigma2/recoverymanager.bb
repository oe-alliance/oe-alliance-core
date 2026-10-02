SUMMARY = "Open Recovery Manager (ORM), steps in when enigma2 does not start"
DESCRIPTION = "A native menu drawn with LVGL: shows where the start of enigma2 stopped and offers to restart, disable plugins, update, reset, back up, flash and Remote Support."
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "oe-alliance"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"
HOMEPAGE = "https://github.com/oe-alliance/OpenRecoveryManager"

DEPENDS = "gettext-native"

inherit gittag upx-compress

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "${GITPKGVTAG}"
PR = "r0"

SRC_URI = "gitsm://github.com/oe-alliance/OpenRecoveryManager.git;protocol=https;branch=master"

EXTRA_OEMAKE = "LOCALEDIR=${datadir}/locale"

PACKAGE_NO_LOCALE = "1"
FILES:${PN}-locale = ""
FILES:${PN} += "${datadir}/locale ${libdir}/enigma2/python/Plugins/SystemPlugins/RecoveryManager"

RPROVIDES:${PN} += "orm"

do_install() {
    oe_runmake install DESTDIR=${D}
}
