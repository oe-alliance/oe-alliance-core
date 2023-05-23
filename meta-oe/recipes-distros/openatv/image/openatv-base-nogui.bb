SUMMARY = "nogui addon Base packages"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY:${PN} = "1"

PV = "1.0"
PR = "r0"

#PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RRECOMMENDS:${PN} = "\
    openatv-enigma2 \
    openatv-spinner \
    ${PYTHON_PN}-pillow \
    ${PYTHON_PN}-service-identity \
    ${PYTHON_PN}-requests \
    ${PYTHON_PN}-future \
    ${PYTHON_PN}-pexpect \
    ${PYTHON_PN}-six \
"
