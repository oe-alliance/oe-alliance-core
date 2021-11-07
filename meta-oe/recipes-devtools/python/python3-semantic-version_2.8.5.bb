SUMMARY = "A library implementing the 'SemVer' scheme."
HOMEPAGE = "https://github.com/rbarrois/python-semanticversion"
AUTHOR = "Raphaël Barrois <raphael.barrois+semver@polytechnique.org>"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4fb31e3c1c7eeb8b5e8c07657cdd54e2"

inherit setuptools3

SRC_URI = "https://files.pythonhosted.org/packages/d4/52/3be868c7ed1f408cb822bc92ce17ffe4e97d11c42caafce0589f05844dd0/semantic_version-2.8.5.tar.gz"
SRC_URI[md5sum] = "76d7364def7ee487b6153d40b13de904"
SRC_URI[sha256sum] = "d2cb2de0558762934679b9a104e82eca7af448c9f4974d1f3eeccff651df8a54"

S = "${WORKDIR}/semantic_version-2.8.5"

BBCLASSEXTEND = "native nativesdk"
