SUMMARY = "Firmware for rt73 based USB wifi adaptors"
LICENSE = "unknown"

PR = "r1"

SRC_URI = "http://www.ralinktech.com.tw/data/RT71W_Firmware_V${PV}.zip"

S = "${WORKDIR}/RT71W_Firmware_V${PV}"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 rt73.bin ${D}${nonarch_base_libdir}/firmware/
}

FILES_${PN} = "${nonarch_base_libdir}/firmware/"

inherit allarch

SRC_URI[md5sum] = "1e7a5dc574e0268574fcda3fd5cf52f7"
SRC_URI[sha256sum] = "e2d3ac1aaf4815e0c9531bc3fb7f56f30604cd52351332f1f8d6a28bb9a953bf"
