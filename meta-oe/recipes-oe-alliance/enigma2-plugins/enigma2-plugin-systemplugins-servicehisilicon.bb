DESCRIPTION = "hisilicon service for enigma2"
AUTHOR = "zgemma-star"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"
require conf/python/python3-compileall.inc

DEPENDS = "enigma2"
RDEPENDS:${PN} = "enigma2"

SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/oe-mirrors/servicehisilicon.git;protocol=https;branch=master"
SRC_URI:openatv = "git://github.com/oe-mirrors/servicehisilicon.git;protocol=https;branch=openatv"
SRC_URI:openhdf = "git://github.com/oe-mirrors/servicehisilicon.git;protocol=https;branch=openatv"
SRC_URI:openvix = "git://github.com/OpenViX/servicehisilicon.git;protocol=https;branch=sigc3"
SRC_URI:openbh = "git://github.com/BlackHole/servicehisilicon.git;protocol=https;branch=sigc3"

S = "${WORKDIR}/git"

inherit autotools gitpkgv ${PYTHON_PN}native pkgconfig ${PYTHON_PN}targetconfig

PV = "git"
PKGV = "git${GITPKGV}"

EXTRA_OECONF = " \
	BUILD_SYS=${BUILD_SYS} \
	HOST_SYS=${HOST_SYS} \
	STAGING_INCDIR=${STAGING_INCDIR} \
	STAGING_LIBDIR=${STAGING_LIBDIR} \
	"

FILES:${PN} = "\
	${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceHisilicon/*.pyc \
	${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceHisilicon/servicehisilicon.so \
	"

FILES:${PN}-dev = "\
	${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceHisilicon/*.py \
	${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceHisilicon/servicehisilicon.la \
	"

do_compile:openhdf:prepend() {
        sed -i "s/PyInt/PyLong/g" ${S}/servicehisilicon/servicehisilicon.cpp
}
