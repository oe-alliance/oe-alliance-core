DESCRIPTION = "Bitrate"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

DEPENDS = "autoconf-native automake-native libtool-native pkgconfig-native"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins pkgconfig

SRC_URI = "git://github.com/oe-alliance-plugins/Bitrate.git;protocol=https;branch=main"

FILES:${PN} += "${bindir}"

do_install:append() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/Bitrate/bitratetool/bitrate ${D}${bindir}/bitrate
}

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
