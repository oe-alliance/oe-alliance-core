DESCRIPTION = "iniparse is a INI parser for Python"
HOMEPAGE = "http://code.google.com/p/iniparse/"
SECTION = "devel/python"
PRIORITY = "optional"

require conf/license/license-gplv2.inc



SRC_URI[md5sum] = "29005e7585a90e6a09c881dc3b30edee"
SRC_URI[sha256sum] = "d9d85dd9c073ea021f8b46670bf075bc3de228ac00c717ca20834821ae73dc80"
SRC_URI = "http://iniparse.googlecode.com/files/iniparse-${PV}.tar.gz"
S = "${WORKDIR}/iniparse-${PV}"

inherit distutils
