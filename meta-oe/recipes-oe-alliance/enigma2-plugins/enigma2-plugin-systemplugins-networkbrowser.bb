DESCRIPTION = "Networkbrowser and Network-Mountmanager"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

DEPENDS = "enigma2 libtirpc nmap autoconf-native automake-native libtool-native pkgconfig-native"

CFLAGS += "-I${STAGING_INCDIR}/tirpc"
LDFLAGS += "-ltirpc"
CXXFLAGS = " -std=c++11"

RDEPENDS:${PN} = "autofs smbclient"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins pkgconfig python3targetconfig
 
SRC_URI = "git://github.com/oe-alliance-plugins/NetworkBrowser.git;protocol=https;branch=main"

CPPFLAGS:append = " -I${STAGING_INCDIR}/python${PYTHON_BASEVERSION}"

FILES:${PN} += "${libdir}/enigma2/python/Plugins/SystemPlugins/NetworkBrowser"

do_configure:append() {
    cd ${S}
    autoreconf -fi
    ./configure \
        --build=${BUILD_SYS} \
        --host=${HOST_SYS} \
        --target=${TARGET_SYS} \
        --prefix=${prefix} \
        --exec_prefix=${exec_prefix} \
        --bindir=${bindir} \
        --sbindir=${sbindir} \
        --libdir=${libdir} \
        --includedir=${includedir}
}

do_compile:append() {
    oe_runmake -C ${S}
}

do_install:append() {
    oe_runmake -C ${S} DESTDIR=${D} install
}