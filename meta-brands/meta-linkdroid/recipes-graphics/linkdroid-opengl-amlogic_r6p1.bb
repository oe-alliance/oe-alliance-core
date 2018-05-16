SUMMARY = "libgles for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH := "${MACHINE_ARCH}"
require conf/license/license-close.inc


PR="r0"

PROVIDES = "virtual/libgles1 virtual/libgles2 virtual/egl"
RPROVIDES_${PN} = "virtual/libgles1 virtual/libgles2 virtual/egl"


SRC_URI = "file://opengl-905-r6p1.tgz \
           file://10-meson_mali.rules \
" 

S = "${WORKDIR}/usr"

INHIBIT_PACKAGE_STRIP = "1"
do_compile() {
}

do_install() {
    install -d ${D}${sysconfdir}/udev/rules.d
    install -d ${D}${includedir}
    install -d ${D}${libdir}
    install -m 0644 ${WORKDIR}/10-meson_mali.rules  ${D}${sysconfdir}/udev/rules.d/
    cp -axr ${S}/include/* ${D}${includedir}/
    cp -ax ${S}/lib/* ${D}${libdir}/
}

do_package_qa() {
}

FILES_${PN} = "${includedir}/* ${libdir}/*  ${sysconfdir}/*"
FILES_${PN}-dev = "/usr/include/*"
