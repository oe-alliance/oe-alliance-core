SUMMARY = "XML C Parser Library and Toolkit for old Close QT Plugins"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
require conf/license/license-close.inc

S = "${UNPACKDIR}/lib"

SRC_URI += "file://libxml2.so.2.zip"

SRC_URI[md5sum] = "f70618b17e38c10b8f11fc84d5078f50"
SRC_URI[sha256sum] = "eaa934baa5abdac058f0177cf763b46563182db7b86b4691f2ec95e64dfd8082"

do_compile() {
}

do_install() {
    install -d ${D}/usr/lib
    cp -rf ${S}/* ${D}/usr/lib/
}

do_package_qa() {
}

sysroot_stage_all() {
}

INSANE_SKIP:${PN} += "installed-vs-shipped ldflags file-rdeps dev-so"
SOLIBS = ".so"
FILES_SOLIBSDEV = ""
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"

PACKAGES = "${PN}"
FILES:${PN} = "/usr/lib/"
