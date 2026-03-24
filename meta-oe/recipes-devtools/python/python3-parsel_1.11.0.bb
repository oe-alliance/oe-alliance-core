SUMMARY  = "library to extract and remove data from HTML and XML using XPath and CSS selectors, optionally combined with regular expressions"
HOMEPAGE = "https://parsel.readthedocs.org"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=df1964f04d94b7abc066a5f878be560a"

DEPENDS += "python3-hatch-vcs-native"

RDEPENDS:${PN} = "python3-cssselect python3-jmespath python3-lxml python3-packaging python3-w3lib"

SRC_URI[md5sum] = "b154f0c24f3c810cb52ba250097eebc2"
SRC_URI[sha256sum] = "5925fe087eb16fc404a7ed91e31e2c1e2a9b230da4b64f34d81358c0d0e27e88"

inherit pypi python_hatchling

include python3-package-split.inc
