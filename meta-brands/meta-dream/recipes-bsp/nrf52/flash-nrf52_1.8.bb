SUMMARY = "Flasher for NRF52 based BLE Transceiver"

SRC_URI[aarch64.md5sum] = "01e790af99887020fbcd618341e60671"
SRC_URI[aarch64.sha256sum] = "f4c6692dbe6feebc747860f79931235b67604a840b3d7e0052b8080f80770e40"

COMPATIBLE_MACHINE = "^(dreamone|dreamtwo)$"

inherit opendreambox-precompiled-binary4

RDEPENDS:${PN} = "libcrypto10"

INSANE_SKIP:${PN} += "file-rdeps ldflags"
