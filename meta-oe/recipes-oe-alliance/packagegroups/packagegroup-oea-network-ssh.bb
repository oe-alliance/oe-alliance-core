SUMMARY = "OE-Alliance SSH and SFTP services"
DESCRIPTION = "Installable service group used by Enigma2's Network Services screen."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit packagegroup

PACKAGE_ARCH = "${MACHINE_ARCH}"
ALLOW_EMPTY:${PN} = "1"

RDEPENDS:${PN} = "\
    dropbear \
    openssh-sftp-server \
    "
