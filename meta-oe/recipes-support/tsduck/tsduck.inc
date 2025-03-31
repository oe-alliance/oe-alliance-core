MAINTAINER = "https://tsduck.io/"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.txt;beginline=1;endline=1;md5=c3af740e8628bb461ed34cc1b4bba078"

SRC_URI = "git://github.com/tsduck/tsduck.git;protocol=https;branch=master"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "${GITPKGVTAG}"
PR = "r0"

S = "${WORKDIR}/git"
