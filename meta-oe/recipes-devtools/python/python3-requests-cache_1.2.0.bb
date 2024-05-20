SUMMARY = "A transparent persistent cache for the requests library"
HOMEPAGE = "https://github.com/reclosedev/requests-cache"
AUTHOR = "Roman Haritonov <>"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=66ca615c6f22205d5254d6c230305c92"

PYPI_PACKAGE = "requests_cache"

inherit pypi python_poetry_core

SRC_URI[md5sum] = "6ddc0516be3e76bab56589fe946501df"
SRC_URI[sha256sum] = "db1c709ca343cc1cd5b6c8b1a5387298eceed02306a6040760db538c885e3838"

RDEPENDS:${PN} = "python3-requests python3-urllib3 python3-appdirs python3-attrs python3-cattrs python3-url-normalize"

include python3-package-split.inc
