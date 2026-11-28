SUMMARY = "GTMedia GTX Combo C21 transport router firmware and board profile"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "${AMLOGIC_SOURCE_MIRROR}/gtx-c21-cimax-usbdvb.bin;name=firmware \
           ${AMLOGIC_SOURCE_MIRROR}/gtx-c21-cimax-usb.cfg;name=profile"
SRC_URI[firmware.sha256sum] = "b889dd5ee7ca0e99878d7ce96c579075aeaef8c0187649051a02d0fe324e15e7"
SRC_URI[profile.sha256sum] = "0c3424c1167f4d5f5e98c700e7a0842e509a3ec850e79cd23ed31c1f61dbb3b6"
S = "${UNPACKDIR}"

do_install() {
    install -Dm0644 ${S}/gtx-c21-cimax-usbdvb.bin ${D}${nonarch_base_libdir}/firmware/cimax+_usbdvb.bin
    install -Dm0644 ${S}/gtx-c21-cimax-usb.cfg ${D}${nonarch_base_libdir}/firmware/cimax+usb.cfg
    # OUT0 carries satellite. Pass CH0-IN directly until a CI session requests slot A.
    profile="${D}${nonarch_base_libdir}/firmware/cimax+usb.cfg"
    sed -i -e 's/ROUTER_CAM_CH[[:blank:]]\+0x00/ROUTER_CAM_CH 0x01/' \
        -e 's/CH0 \& CH1 from CAM A \& B/CH0 direct, CH1 from CAM B/' "$profile"
    grep -q '^ROUTER_CAM_CH 0x01' "$profile"
}

FILES:${PN} = "${nonarch_base_libdir}/firmware/cimax+_usbdvb.bin ${nonarch_base_libdir}/firmware/cimax+usb.cfg"
