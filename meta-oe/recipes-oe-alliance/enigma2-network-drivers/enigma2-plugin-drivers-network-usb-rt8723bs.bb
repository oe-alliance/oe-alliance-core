SUMMARY = "WiFi devices for Realtek 8723bs chipsets."
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = "\
    ${@bb.utils.contains("MACHINE_FEATURES", "linux4extra", "kernel-module-r8723bs firmware-rtl8723bs", "rt8723bs", d)} \
"

PV = "1.0"
PR = "r1"

ALLOW_EMPTY_${PN} = "1"
