RDEPENDS:${PN} += "flash-nrf52 (>= 1.8)"

CURRENT_FW = "central-two-noreset-200922-1.14.hex"

include nrf52-firmware.inc

COMPATIBLE_MACHINE = "^(dreamtwo)$"
