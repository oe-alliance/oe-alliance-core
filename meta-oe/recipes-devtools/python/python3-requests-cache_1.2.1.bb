SUMMARY = "A transparent persistent cache for the requests library"
HOMEPAGE = "https://github.com/reclosedev/requests-cache"
AUTHOR = "Roman Haritonov <>"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=66ca615c6f22205d5254d6c230305c92"

PYPI_PACKAGE = "requests_cache"

inherit pypi python_poetry_core

SRC_URI[md5sum] = "27038cb33985f5b144cf32107151921a"
SRC_URI[sha256sum] = "68abc986fdc5b8d0911318fbb5f7c80eebcd4d01bfacc6685ecf8876052511d1"

RDEPENDS:${PN} = "python3-requests python3-urllib3 python3-appdirs python3-attrs python3-cattrs python3-url-normalize"

include python3-package-split.inc
