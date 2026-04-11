SUMMARY = "HTTPX - A next-generation HTTP client for Python."
HOMEPAGE = "https://github.com/encode/httpx"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://CHANGELOG.md;md5=962482afe40daef9016a365e1dbef36c"

DEPENDS += "python3-setuptools-scm-native python3-hatchling-native python3-hatch-fancy-pypi-readme-native"

RDEPENDS:${PN} = "python3-certifi python3-httpcore python3-anyio python3-idna"

SRC_URI[md5sum] = "774cf32b9cd94a44189fbe5871026664"
SRC_URI[sha256sum] = "75e98c5f16b0f35b567856f597f06ff2270a374470a5c2392242528e3e3e42fc"

inherit pypi python_hatchling

include python3-package-split.inc
