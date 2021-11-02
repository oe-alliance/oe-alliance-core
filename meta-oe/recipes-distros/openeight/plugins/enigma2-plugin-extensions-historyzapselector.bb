DESCRIPTION = "Advanced history zap selector"
HOMEPAGE = "https://github.com/Dima73/enigma2-plugin-extensions-historyzapselector"
LICENSE = "PD"
LIC_FILES_CHKSUM = "file://README;md5=b5f3c7ed409cbcc6d849e694f25729ba"
SRC_URI = "git://github.com/Dima73/enigma2-plugin-extensions-historyzapselector.git;protocol=https"
S = "${WORKDIR}/git"

inherit gitpkgv
PV = "1+git${SRCPV}"
PKGV = "1+git${GITPKGV}"
PR = "r0"

SRCREV="8ce31b693e0f07870bd7c91ad070c5ff91d9ff81"

inherit distutils-openplugins
