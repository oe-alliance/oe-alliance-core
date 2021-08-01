SUMMARY = "Compatibility for packages that link to python"

require conf/license/license-gplv2.inc

RDEPENDS:${PN} = "${PYTHON_PN}-core"

do_install () {
    install -d ${D}${bindir}
    ln -sf python3 ${D}${bindir}/python
}

FILES:${PN} = "${bindir}"
