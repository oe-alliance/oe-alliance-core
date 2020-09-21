require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

SRC_URI += " file://linux_4_1_1_17_dvbs2x.patch"

SRC_URI_append_vuduo4k += "file://linux_4.1.20_dmx_source_dvr.patch"
SRC_URI_append_vuduo4kse += "file://linux_4.1.20_dmx_source_dvr.patch"

SRC_URI[md5sum] = "c07e1bced97a4d3cd9095b3440b48b08"
SRC_URI[sha256sum] = "76700a6a788c5750e3421eba004190cdc5b63f62726fce3b5fa27bd1c2cd5912"
