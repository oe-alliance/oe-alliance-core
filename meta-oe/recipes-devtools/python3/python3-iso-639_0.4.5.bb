SUMMARY = "Python library for ISO 639 standard"
HOMEPAGE = "https://github.com/noumar/iso639"
AUTHOR = "Mikael Karlsson <i8myshoes@gmail.com>"
LICENSE = "AGPLv3"
LIC_FILES_CHKSUM = "file://setup.py;md5=985ce5e887f5e0440113829ba28ebe27"

PROVIDES += "python3-iso639"
RPROVIDES_${PN} += "python3-iso639"

SRC_URI = "https://files.pythonhosted.org/packages/5a/8d/27969852f4e664525c3d070e44b2b719bc195f4d18c311c52e57bb93614e/iso-639-0.4.5.tar.gz"
SRC_URI[md5sum] = "cc282daf57f57061a9309f2567bff052"
SRC_URI[sha256sum] = "dc9cd4b880b898d774c47fe9775167404af8a85dd889d58f9008035109acce49"

S = "${WORKDIR}/iso-639-0.4.5"

inherit setuptools3
