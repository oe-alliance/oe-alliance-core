SUMMARY = "This is a python library for accessing resources protected by OAuth 2.0"
HOMEPAGE = "http://github.com/google/oauth2client"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=88f599f710b9d48dad0929ebd090fc1a"
RDEPENDS_${PN} = "python-httplib2 python-six python-uritemplate"

SRCREV = "c815c2babd6ed811ba426e014f03978d48f59d9c"

SRC_URI = "git://github.com/google/oauth2client.git;protocol=git;branch=master"

S = "${WORKDIR}/git"

inherit setuptools


