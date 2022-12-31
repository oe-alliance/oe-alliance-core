RDEPENDS:${PN} += "flash-nrf52 (>= 1.8)"

CURRENT_FW = "central-one-noreset-220114-1.15.hex"

include nrf52-firmware.inc

COMPATIBLE_MACHINE = "^(dreamone)$"


