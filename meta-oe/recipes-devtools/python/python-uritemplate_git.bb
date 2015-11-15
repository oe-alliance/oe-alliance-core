SUMMARY = "Download videos from youtube.com or other video platforms"
HOMEPAGE = "http://rg3.github.io/youtube-dl/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://README.rst;beginline=56;endline=72;md5=cd4ec7c13091e5ae47fdf9c27505c1c9"

SRCREV = "1e780a49412cdbb273e9421974cb91845c124f3f"

SRC_URI = "git://github.com/uri-templates/uritemplate-py.git;protocol=git;branch=master"

S = "${WORKDIR}/git"

inherit distutils

RDEPENDS_${PN} = "python"
