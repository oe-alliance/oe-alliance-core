require vuplus-bluetooth-util.inc

COMPATIBLE_MACHINE = "^(vuduo4kse)$"

SRCDATE = "20250529"
SRCDATE_PR = "r0"

CYW4373_FIRMWARE_PATH="cypress"
CYW4373_FIRMWARE_FILE="BCM4373A0-04b4-640c.hcd"

do_install:append() {
    if [ -n "${CYW4373_FIRMWARE_FILE}" ]; then
        install -d ${D}/lib/firmware/${CYW4373_FIRMWARE_PATH}
        install -m 0644 ${UNPACKDIR}/vuplus-bluetooth-util-${MACHINE}/${CYW4373_FIRMWARE_FILE} ${D}/lib/firmware/${CYW4373_FIRMWARE_PATH}
    fi
}

SRC_URI[md5sum] = "58f97f579e7c43341405673e2b55a82c"
SRC_URI[sha256sum] = "f7df851e33461aed781eb4d912a3941fcf6bac35a36b4e86e333f0d04665e5c0"
