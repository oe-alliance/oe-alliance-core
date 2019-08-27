SUMMARY = "Firmware files for use with Linux kernel"
SECTION = "kernel"

LICENSE = "CLOSED"


PE = "1"

SRC_URI = "file://ap6212-wifi-firmware.zip \
           file://config.txt \
           file://ap6212-bluetooth \
           file://ap6212-default \
"

S = "${WORKDIR}/6212"

inherit allarch update-alternatives update-rc.d

INITSCRIPT_NAME = "ap6212"
INITSCRIPT_PARAMS = "defaults 10"


do_compile() {
    :
}

FILES_${PN} = " \
                ${nonarch_base_libdir} \
                /etc \
"

do_install() {
    install -d  ${D}/etc/firmware/ap6212
    install -d  ${D}${nonarch_base_libdir}/firmware/ap6212
    install -m 0655 nvram.txt ${D}${nonarch_base_libdir}/firmware/ap6212
    install -m 0655 ${WORKDIR}/config.txt ${D}${nonarch_base_libdir}/firmware/ap6212
    install -m 0655 fw_bcm43438a0.bin ${D}${nonarch_base_libdir}/firmware/ap6212
    install -m 0655 fw_bcm43438a0_apsta.bin ${D}${nonarch_base_libdir}/firmware/ap6212
    install -m 0655 fw_bcm43438a0_p2p.bin ${D}${nonarch_base_libdir}/firmware/ap6212
    install -m 0655 bcm43438a0.hcd ${D}${nonarch_base_libdir}/firmware/ap6212/4343A0.hcd
    
    # Bluetooth
    install -m 0655 bcm43438a0.hcd ${D}/etc/firmware/ap6212/4343A0.hcd

    # Only install the init script when 'sysvinit' is in DISTRO_FEATURES.
    if ${@bb.utils.contains('DISTRO_FEATURES', 'sysvinit', 'true', 'false', d)}; then
        install -d "${D}${sysconfdir}/init.d"
        install -m 0755 ${WORKDIR}/ap6212-bluetooth ${D}${sysconfdir}/init.d/ap6212

        install -d "${D}${sysconfdir}/default"
        install -m 0644 ${WORKDIR}/ap6212-default ${D}${sysconfdir}/default/ap6212
    fi
}
