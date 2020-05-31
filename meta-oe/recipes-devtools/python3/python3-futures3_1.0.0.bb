DESCRIPTION = "The concurrent.futures module provides a high-level interface for asynchronously executing callables."
SECTION = "devel/python"
LICENSE = "PSF"
LIC_FILES_CHKSUM = "file://README.rst;md5=fb31741d707bf8336d5f43dd164a1a4d"
HOMEPAGE = "https://github.com/mihaiparvu/futures3"
DEPENDS = "${PYTHON_PN}"

PROVIDES += "python3-futures"
RPROVIDES_${PN} += "python3-futures"

SRC_URI[md5sum] = "897f50d198bd4bc0f60415dea4b5bb56"
SRC_URI[sha256sum] = "12750f871ad31eba4deeac6c087963be72638cc6f570b90f927678a718574734"

inherit pypi setuptools3

BBCLASSEXTEND = "native nativesdk"
