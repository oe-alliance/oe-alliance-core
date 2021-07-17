SUMMARY  = "Websocket client for Python"
HOMEPAGE = "https://github.com/websocket-client/websocket-client"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c96ca6c1de8adc025adfada81d06fba5"

SRC_URI[md5sum] = "19ccf9abcd151b30975e7b52bfd02760"
SRC_URI[sha256sum] = "d376bd60eace9d437ab6d7ee16f4ab4e821c9dae591e1b783c58ebd8aaf80c5c"

inherit pypi setuptools

RDEPENDS_${PN} = "\
    ${PYTHON_PN}-six \
    ${PYTHON_PN}-backports-ssl \
"
