SUMMARY = "Python template engine and code generation tool"
HOMEPAGE = "https://cheetahtemplate.org/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=6c8d05debf9d3d283931051ce5232fe7"

inherit setuptools3

SRCREV = "63d7ef20b213907d6c9340f60c92012934cfd462"
SRC_URI = "git://github.com/CheetahTemplate3/cheetah3;protocol=https;branch=master"
SRC_URI[sha256sum] = "58b5d84e5fbff6cf8e117414b3ea49ef51654c02ee887d155113c5b91d761967"

S = "${WORKDIR}/git"

RDEPENDS:${PN} = "python3-pickle python3-pprint"
RDEPENDS:${PN}:class-native = ""

BBCLASSEXTEND = "native nativesdk"

include python3-package-split.inc
