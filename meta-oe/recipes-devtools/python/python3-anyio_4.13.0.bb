SUMMARY = "High-level concurrency and networking framework on top of asyncio or Trio"
HOMEPAGE = "https://github.com/agronholm/anyio"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c0a769411d2af7894099e8ff75058c9f"

DEPENDS += "python3-setuptools-scm-native python3-hatchling-native python3-hatch-fancy-pypi-readme-native"

RDEPENDS:${PN} = "python3-idna"

SRC_URI[md5sum] = "790c9bef76fdeac4c2e4e4ea757e14ab"
SRC_URI[sha256sum] = "334b70e641fd2221c1505b3890c69882fe4a2df910cba14d97019b90b24439dc"

inherit pypi python_setuptools_build_meta

include python3-package-split.inc
