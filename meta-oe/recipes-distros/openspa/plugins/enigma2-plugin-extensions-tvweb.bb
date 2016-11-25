NAME = "TVweb"
SUMMARY = "View films online from your STB"
SECTION = "extra"
PRIORITY = "optional"
RDEPENDS_${PN} = "wget rtmpdump"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
VER ="1.0"
PR = "r7"

SRC_URI = "git://github.com/OpenSPA/TVWeb.git;protocol=git"


S="${WORKDIR}"


python do_package_write_ipk() {
    packages = d.getVar('PACKAGES', True)
    if not packages:
        bb.debug(1, "No PACKAGES defined, nothing to package")
        return

    bb.build.exec_func("read_subpackage_metadata", d)
    d.setVar('SRC_URI', 'unknown')
    bb.build.exec_func("do_package_ipk", d)
}

do_install() {

    cp -rp ${S}/git/* ${D}/
    rm ${D}/README.md
}

FILES_${PN} = "/"
