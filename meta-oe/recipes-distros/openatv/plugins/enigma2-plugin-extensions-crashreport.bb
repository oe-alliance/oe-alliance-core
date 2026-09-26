SUMMARY = "OpenATV private crash reports and receiver diagnostics"
MAINTAINER = "OpenATV"
HOMEPAGE = "https://github.com/openatv/enigma2-plugin-extensions-crashreport"
SECTION = "extra"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://../LICENSE.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"
require conf/python/python3-compileall.inc

inherit allarch gittag setuptools3-openplugins

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "${GITPKGVTAG}"
BRANCH = "main"

SRC_URI = "git://github.com/openatv/enigma2-plugin-extensions-crashreport.git;protocol=https;branch=${BRANCH}"

RDEPENDS:${PN} = "enigma2 python3-compression python3-json python3-netclient python3-misc python3-subprocess python3-twisted-core python3-qrcode python3-pillow"

PLUGIN_DIR = "${libdir}/enigma2/python/Plugins/Extensions/CrashReport"
FILES:${PN} = "${PLUGIN_DIR}/*.pyc ${PLUGIN_DIR}/*.png ${PLUGIN_DIR}/locale"
FILES:${PN}-src = "${PLUGIN_DIR}/*.py"
