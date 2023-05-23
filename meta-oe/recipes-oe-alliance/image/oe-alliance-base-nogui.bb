SUMMARY = "nogui addon Base packages"
LICENSE = "MIT"
PACKAGE_ARCH = "${MACHINEBUILD}"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit packagegroup

ALLOW_EMPTY:${PN} = "1"
PACKAGES = "${PN}"

PV = "${IMAGE_VERSION}"
PR = "r0"

RRECOMMENDS:${PN} = "\
    oe-alliance-enigma2 \
"

