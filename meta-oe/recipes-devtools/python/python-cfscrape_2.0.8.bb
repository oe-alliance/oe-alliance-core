SUMMARY = "A simple Python module to bypass Cloudflare's anti-bot page."
DESCRIPTION = "See https://github.com/Anorov/cloudflare-scrape for more information."
HOMEPAGE = "https://github.com/Anorov/cloudflare-scrape"
AUTHOR = "Anorov <anorov.vorona@gmail.com>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=93d4804f061e05530be1a85b24185408"

RDEPENDS_${PN} = "python-js2py python-requests"

SRC_URI = "https://files.pythonhosted.org/packages/f9/c2/271fbe5bbe2ca78daed14478cec7b2c1af8242c8960873d9eb841fbcf6c8/cfscrape-${PV}.tar.gz"
SRC_URI[md5sum] = "653c2347740b3cfca20179f2ddada468"
SRC_URI[sha256sum] = "55913cb9ff2f39ad796fad2ba8888f12707daba4d8a28f3e34957804a4d8ecb8"

S = "${WORKDIR}/cfscrape-${PV}"

inherit setuptools

include python-package-split.inc
