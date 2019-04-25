DESCRIPTION = "iniparse is a INI parser for Python"
HOMEPAGE = "https://github.com/candlepin/python-iniparse"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "MIT | PSF"
LIC_FILES_CHKSUM = "file://LICENSE;md5=52f28065af11d69382693b45b5a8eb54"

SRC_URI = "https://files.pythonhosted.org/packages/0f/d1/3090ef9be165da5ddb1b0cf2b332d3282588bdd2dd0967e94b547f10055f/iniparse-${PV}.tar.gz"

SRC_URI[md5sum] = "5e573e9e9733d97623881ce9bbe5eca6"
SRC_URI[sha256sum] = "abc1ee12d2cfb2506109072d6c21e40b6c75a3fe90a9c924327d80bc0d99c054"

S = "${WORKDIR}/iniparse-${PV}"

inherit distutils

include python-package-split.inc
