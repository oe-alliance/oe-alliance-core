SUMMARY = "A modern CSS selector implementation for Beautiful Soup"
HOMEPAGE = "https://github.com/facelessuser/soupsieve"
AUTHOR = "Isaac Muse <Isaac.Muse@gmail.com>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=5a6fd3b0c24fc5a041a3d1bbb22c81b5"

RDEPENDS_${PN} = "python-backports-functools-lru-cache"

SRC_URI = "https://files.pythonhosted.org/packages/7f/4e/95a13527e18b6f1a15c93f1c634b86d5fa634c5619dce695f4e0cd68182f/soupsieve-${PV}.tar.gz"
SRC_URI[md5sum] = "43d8ea20c58494446aa65ba5cc6320fe"
SRC_URI[sha256sum] = "605f89ad5fdbfefe30cdc293303665eff2d188865d4dbe4eb510bba1edfbfce3"

S = "${WORKDIR}/soupsieve-${PV}"

inherit setuptools
