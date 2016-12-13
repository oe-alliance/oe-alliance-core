# Ensure we get kernel-module-rtl8192cu

# kernel-module-rtl8xxxu doesn't seem to work well for rtl8192cu chips.
# so redefine RRECOMMENDS_ from the *.bb file to include 
# kernel-module-rtl8192cu
#

RRECOMMENDS_${PN} = " \
    ${@base_contains("MACHINE_FEATURES", "linuxwifi", "kernel-module-rtl8xxxu kernel-module-rtl8192cu", "rtl8192cu", d)} \
    firmware-rtl8192cu \
    firmware-rtl8192cufw \
    "
