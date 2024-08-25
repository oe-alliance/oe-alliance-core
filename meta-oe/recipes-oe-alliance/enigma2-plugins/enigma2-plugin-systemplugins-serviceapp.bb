DESCRIPTION = "serviceapp service for enigma2"
AUTHOR = "Maroš Ondrášek <mx3ldev@gmail.com>"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"
require conf/python/python3-compileall.inc

DEPENDS = "enigma2 uchardet openssl"
RDEPENDS:${PN} = "enigma2 uchardet openssl exteplayer3 ${PYTHON_PN}-json"
RCONFLICTS:${PN} = "enigma2-plugin-extensions-serviceapp"
RREPLACES:${PN} = "enigma2-plugin-extensions-serviceapp"

SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/oe-mirrors/serviceapp.git;branch=develop;protocol=https"
SRC_URI:openvix = "git://github.com/OpenViX/serviceapp.git;branch=sigc3;protocol=https"
SRC_URI:openbh = "git://github.com/BlackHole/serviceapp.git;branch=sigc3;protocol=https"
SRC_URI:teamblue = "git://github.com/teamblue-e2/serviceapp.git;branch=develop;protocol=https"


S = "${WORKDIR}/git"

inherit autotools gitpkgv ${PYTHON_PN}native pkgconfig gettext python3targetconfig

CXXFLAGS += "${@bb.utils.contains_any("DISTRO_NAME", "openvix openbh teamblue", "" , " -std=c++11", d)}"

PV = "0.5+git${SRCPV}"
PKGV = "0.5+git${GITPKGV}"

PR = "r3"

EXTRA_OECONF = "\
	BUILD_SYS=${BUILD_SYS} \
	HOST_SYS=${HOST_SYS} \
	STAGING_INCDIR=${STAGING_INCDIR} \
	STAGING_LIBDIR=${STAGING_LIBDIR} \
	"

PACKAGES = "${PN} ${PN}-src ${PN}-dbg"

FILES:${PN} = "\
    ${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceApp/*.pyc \
    ${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceApp/serviceapp.so \
    ${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceApp/locale/*/*/*.mo \
    "

FILES:${PN}-src = "\
    ${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceApp/*.py \
    ${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceApp/locale/*/LC_MESSAGES/*.po \
    /usr/src/debug/* \
    "

FILES:${PN}-dbg = "\
    ${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceApp/serviceapp.la \
    /usr/src/debug/enigma2-plugin-systemplugins-serviceapp/*/*/*/*/*.cpp \
    /usr/src/debug/enigma2-plugin-systemplugins-serviceapp/*/*/*/*/*.h \
    /usr/src/debug/enigma2-plugin-systemplugins-serviceapp/*/*/*/*/*.c \
    /usr/src/debug/enigma2-plugin-systemplugins-serviceapp/*/*/*/*/*/*.cpp \
    /usr/src/debug/enigma2-plugin-systemplugins-serviceapp/*/*/*/*/*/*.h \
    /usr/src/debug/enigma2-plugin-systemplugins-serviceapp/*/*/*/*/*/*.c \
    "
