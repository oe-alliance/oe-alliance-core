SUMMARY = "A transparent persistent cache for the requests library"
HOMEPAGE = "https://github.com/reclosedev/requests-cache"
AUTHOR = "Roman Haritonov <>"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=66ca615c6f22205d5254d6c230305c92"

DEPENDS += "python3-hatch-vcs-native"

PYPI_PACKAGE = "requests_cache"

inherit pypi python_hatchling

SRC_URI[md5sum] = "bd32f07e33dc2ad3059c48fb6612aa06"
SRC_URI[sha256sum] = "784e9d07f72db4fe234830a065230c59eb446489528f271ba288c640897e47c4"

RDEPENDS:${PN} = "python3-requests python3-urllib3 python3-appdirs python3-attrs python3-cattrs python3-platformdirs python3-url-normalize"

include python3-package-split.inc
