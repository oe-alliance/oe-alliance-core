DESCRIPTION = "Change Beyonwiz Remote Control Code"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://../LICENSE.txt;md5=1ebbd3e34237af26da5dc08a4e440464"
require conf/python/python3-compileall.inc

PN = "enigma2-plugin-systemplugins-remotecontrolcode"

COMPATIBLE_MACHINE = "^(beyonwizt2|beyonwizt3|beyonwizt4|beyonwizu4)$"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
# Replace an already installed Xtrend variant, irrespective of its tag version.
PE:beyonwizu4 = "1"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/BeyonwizRemote.git;protocol=https;branch=main"
