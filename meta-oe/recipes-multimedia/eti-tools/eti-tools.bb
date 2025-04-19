SUMMARY = "ETI conversion software"
DESCRIPTION = "This is a software collection for converting Ensemble Transport Interface \
	used in terrestrial Digital Audio Broadcasting (DAB/DAB+/T-DMB).\
	The main purpose of these apps is to convert/manupulate ETI-NA/ETI-NI streams \
	(by using pipelines)."
LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b278a92d2c1509760384428817710378" 

inherit gitpkgv

PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r0"

SRC_URI = "git://github.com/satdreamgr/eti-tools.git;protocol=https;branch=master"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

inherit autotools-brokensep pkgconfig

PARALLEL_MAKE = ""
