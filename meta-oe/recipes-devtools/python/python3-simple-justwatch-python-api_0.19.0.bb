SUMMARY = "A simple unofficial JustWatch Python API which uses GraphQL to access JustWatch data"
HOMEPAGE = "https://github.com/Electronic-Mango/simple-justwatch-python-api"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=85e89aa53d5cf252d64a061998c20c33"

DEPENDS += "python3-uv-build-native"

PYPI_PACKAGE = "simple_justwatch_python_api"

RDEPENDS:${PN} = "python3-httpx"

SRC_URI[md5sum] = "d3fe3e84d28eb2d7ca7ad52bceb7682b"
SRC_URI[sha256sum] = "7b93b45b86e0a37ae9bafac8c318bd3c1354bb9c6a6c921fbffd55cbe1b9235b"

inherit pypi python_pep517

include python3-package-split.inc
