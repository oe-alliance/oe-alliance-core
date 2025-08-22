DESCRIPTION = "Python logging"
SECTION = "devel/python"
MAINTAINER = "Delgan"
HOMEPAGE = "https://github.com/Delgan/loguru"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${OE-ALLIANCE_BASE}/LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

PV="0.6.0"

RDEPENDS:${PN} = "${PYTHON_PN}-multiprocessing"

inherit ${PYTHON_PN}-dir gitpkgv setuptools3

SRCREV = "30ee26aa4091548b8fade46f7c60b78fd2db3923"

SRC_URI = "git://github.com/Delgan/loguru;protocol=https;branch=master"

S = "${WORKDIR}/git"

