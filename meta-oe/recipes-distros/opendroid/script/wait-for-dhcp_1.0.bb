SUMMARY = "Enigma2 DHCP wait helper"
DESCRIPTION = "Fixes issues with slow DHCP servers when used with softcams"
SECTION = "base"
require conf/license/license-gplv2.inc

SRC_URI = "file://wait_for_dhcp.sh \
           file://waitfordhcp"

S = "${UNPACKDIR}"

do_install() {
    install -d ${D}/usr/bin
    install -m 0755 ${S}/wait_for_dhcp.sh ${D}/usr/bin/wait_for_dhcp.sh

    install -d ${D}/etc/init.d
    install -m 0755 ${S}/waitfordhcp ${D}/etc/init.d/waitfordhcp
}

pkg_postinst:${PN} () {
    if [ -n "$D" ]; then
        install -d $D/etc/rc3.d
        ln -sf ../init.d/waitfordhcp $D/etc/rc3.d/S40waitfordhcp
    else
        update-rc.d waitfordhcp defaults
    fi
}

FILES:${PN} += " /usr/bin /etc/init.d /etc/rc3.d"
