PACKAGE_ARCH = "${MACHINEBUILD}"
PR .= ".2"

EXTRA_OECONF += " --disable-udevrules --enable-hidd"

do_install_append_xc7362() {
    rm -rf ${D}/usr/bin/hciattach
}