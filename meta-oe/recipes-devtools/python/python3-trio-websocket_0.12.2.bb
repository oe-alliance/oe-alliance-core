SUMMARY = "WebSocket library for Trio"
LICENSE = "MIT"
SECTION = "devel/python"
LIC_FILES_CHKSUM = "file://LICENSE;md5=69a70f9681c723a2b4bc1afb1fc68a6f"

PYPI_PACKAGE = "trio_websocket"

include python3-package-split.inc

inherit pypi setuptools3
SRC_URI[sha256sum] = "22c72c436f3d1e264d0910a3951934798dcc5b00ae56fc4ee079d46c7cf20fae"

RDEPENDS:${PN} = " \
	python3-outcome \
	python3-trio \
	python3-wsproto \
"

BBCLASSEXTEND = "native nativesdk"
