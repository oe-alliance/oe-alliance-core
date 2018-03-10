SUMMARY = "A lightweight RDS decoder"
DESCRIPTION = "redsea is an experiment at building a lightweight command-line RDS decoder for Linux/OSX. It works with any RTL-SDR USB radio stick using the rtl_fm tool. It can also decode raw ASCII bitstream, the hex format used by RDS Spy, and multiplex signals (MPX)."
MAINTAINER = "Oona Räisänen <windyoona@gmail.com>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=884926124e31c67b8c1bdaa062802dec"

inherit gitpkgv

PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r0"

SRC_URI = "git://github.com/windytan/redsea.git;protocol=http"

SRCREV = "aeed25e6eccd1da1924bbbb4af0c5fb49cfc962a"

S = "${WORKDIR}/git"

DEPENDS = "liquid-dsp"

inherit autotools-brokensep

EXTRA_OECONF += "--disable-tmc --without-sndfile --without-macports"
