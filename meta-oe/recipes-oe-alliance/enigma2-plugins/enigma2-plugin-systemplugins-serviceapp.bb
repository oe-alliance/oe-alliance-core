DESCRIPTION = "serviceapp service for enigma2"
AUTHOR = "Maroš Ondrášek <mx3ldev@gmail.com>"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS = "enigma2 uchardet openssl"
RDEPENDS_${PN} = "enigma2 uchardet openssl exteplayer3 python-json"
RCONFLICTS_${PN} = "enigma2-plugin-extensions-serviceapp"
RREPLACES_${PN} = "enigma2-plugin-extensions-serviceapp"

SRCREV = "${AUTOREV}"
SRCREV_openatv = "02956ea6b05a0186667582f9f25491f18334d31b"
SRCREV_openeight = "02956ea6b05a0186667582f9f25491f18334d31b"
SRCREV_opendroid = "02956ea6b05a0186667582f9f25491f18334d31b"
SRCREV_openspa = "02956ea6b05a0186667582f9f25491f18334d31b"
SRCREV_opennfr = "02956ea6b05a0186667582f9f25491f18334d31b"
SRC_URI = " \
    git://github.com/mx3L/serviceapp.git;branch=develop \
    "

S = "${WORKDIR}/git"

inherit autotools gitpkgv pythonnative pkgconfig gettext

CXXFLAGS_append = " -std=c++11"

PV = "0.5+git${SRCPV}"
PKGV = "0.5+git${GITPKGV}"

PR = "r0"

EXTRA_OECONF = "\
	BUILD_SYS=${BUILD_SYS} \
	HOST_SYS=${HOST_SYS} \
	STAGING_INCDIR=${STAGING_INCDIR} \
	STAGING_LIBDIR=${STAGING_LIBDIR} \
	"

PACKAGES = "${PN} ${PN}-src ${PN}-dbg"

FILES_${PN} = "\
    ${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceApp/*.pyo \
    ${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceApp/serviceapp.so \
    ${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceApp/locale/*/*/*.mo \
    "

FILES_${PN}-src = "\
    ${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceApp/*.py \
    ${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceApp/locale/*/LC_MESSAGES/*.mo \
    /usr/src/debug/* \
    "

FILES_${PN}-dbg = "\
    ${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceApp/serviceapp.la \
    /usr/src/debug/enigma2-plugin-systemplugins-serviceapp/*/*/*/*/*.cpp \
    /usr/src/debug/enigma2-plugin-systemplugins-serviceapp/*/*/*/*/*.h \
    /usr/src/debug/enigma2-plugin-systemplugins-serviceapp/*/*/*/*/*.c \
    /usr/src/debug/enigma2-plugin-systemplugins-serviceapp/*/*/*/*/*/*.cpp \
    /usr/src/debug/enigma2-plugin-systemplugins-serviceapp/*/*/*/*/*/*.h \
    /usr/src/debug/enigma2-plugin-systemplugins-serviceapp/*/*/*/*/*/*.c \
    "
