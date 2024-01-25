SUMMARY = "A transparent persistent cache for the requests library"
HOMEPAGE = "https://github.com/reclosedev/requests-cache"
AUTHOR = "Roman Haritonov <>"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c2556dbcf8ef52bbb1e5cfb605934354"

PYPI_PACKAGE = "requests_cache"

inherit pypi python_poetry_core

SRC_URI[md5sum] = "e930ad4d4cde8da00695a4e23c8044e4"
SRC_URI[sha256sum] = "764f93d3fa860be72125a568c2cc8eafb151cf29b4dc2515433a56ee657e1c60"

RDEPENDS:${PN} = "python3-requests python3-urllib3 python3-appdirs python3-attrs python3-cattrs python3-url-normalize"

include python3-package-split.inc
