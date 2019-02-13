FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
PR .= ".1"

PACKAGE_ARCH := "${MACHINE_ARCH}"

SRC_URI += " \
    file://67_init_hddown.dpatch \
    file://92_sata-hddown.dpatch \
"

do_install_append() {
    rm ${D}${sysconfdir}/rc*.d/*bootlogd
}

pkg_postinst_${PN}_prepend() {
#!/bin/sh
set -e
	if [ ! -z "$D" ]; then exit 0; fi
}
