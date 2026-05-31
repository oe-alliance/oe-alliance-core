SUMMARY = "OpenSPA smallflash upgrade - packages installed after flash expansion"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit packagegroup

ALLOW_EMPTY:${PN} = "1"

PV = "${IMAGE_VERSION}"

# Backward compatibility: StartWizard installs by old name
RPROVIDES:${PN} = "packagegroup-openspa-small"

RRECOMMENDS:${PN} = "\
    packagegroup-oea-smallflash-core \
    ${E2DEFAULTSKIN} \
    "
