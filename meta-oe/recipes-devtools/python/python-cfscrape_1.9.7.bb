SUMMARY  = "A simple Python module to bypass Cloudflare's anti-bot page."
DESCRIPTION = "See https://github.com/Anorov/cloudflare-scrape for more information."
HOMEPAGE = "https://pypi.org/project/cfscrape"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=93d4804f061e05530be1a85b24185408"

RDEPENDS_${PN} += "python-js2py"

SRC_URI = "https://files.pythonhosted.org/packages/90/f8/3dcbc6d4c0b317de2d12296c9792471bc6e71b1c4be1e253987a2ca21ddb/cfscrape-${PV}.tar.gz"

S = "${WORKDIR}/cfscrape-${PV}"

inherit setuptools

SRC_URI[md5sum] = "31e8cecc07386c898e87dcc77359d077"
SRC_URI[sha256sum] = "fa7fc172e6872f461377216ddd5a6d82c9650b9d10cac5aa0239998daf3e093a"

include python-package-split.inc
