DESCRIPTION = "Python process list module"
SECTION = "devel/python"
PRIORITY = "optional"
SRCNAME = "process"

require conf/license/license-gplv2.inc

DEPENDS = "python"

PV = "1.0"
PKGV = "1.0"
PR = "r1"

SRC_URI = "file://process.tgz"

S = "${WORKDIR}/process"

EXTRA_OECONF = "\
	BUILD_SYS=${BUILD_SYS} \
	HOST_SYS=${HOST_SYS} \
	STAGING_INCDIR=${STAGING_INCDIR} \
	STAGING_LIBDIR=${STAGING_LIBDIR} \
"

inherit autotools

PACKAGES =+ "${PN}-src"
FILES_${PN} = "/usr/lib/python2.7/*.pyo"
FILES_${PN}-src = "/usr/lib/python2.7/*.py"
