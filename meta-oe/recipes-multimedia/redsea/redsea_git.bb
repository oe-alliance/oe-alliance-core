SUMMARY = "A lightweight RDS decoder"
DESCRIPTION = "redsea is an experiment at building a lightweight command-line RDS decoder for Linux/OSX. It works with any RTL-SDR USB radio stick using the rtl_fm tool. It can also decode raw ASCII bitstream, the hex format used by RDS Spy, and multiplex signals (MPX)."
MAINTAINER = "Oona Räisänen <windyoona@gmail.com>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=884926124e31c67b8c1bdaa062802dec"

DEPENDS = "liquid-dsp virtual/libiconv libsndfile1"

inherit gitpkgv

SRCREV = "bf5fcbfe0f41f019c60b95b2178f461103dd13e3"
PV = "0.21+git"
PKGV = "0.21+git${GITPKGV}"

SRC_URI = "git://github.com/windytan/redsea.git;protocol=http;branch=master;protocol=https \
            file://remove-hardcoded-build-path.patch"

S = "${WORKDIR}/git"

inherit pkgconfig meson
