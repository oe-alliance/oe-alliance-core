DESCRIPTION = "U-Boot-Hardkernel - Prebuilt and signed u-boot by hardkernel"
HOMEPAGE = "http://www.mdrjr.net/odroid/mirror/BSPs/Alpha4/unpacked/"
SECTION = "bootloaders"
PROVIDES = "virtual/bootloader"
LICENSE = "GPLv2"

PR = "r1"

PNBLACKLIST[u-boot-hardkernel] ?= "BROKEN: download source doesn't exist; COMPATIBLE_MACHINE isn't specified"

inherit deploy

LIC_FILES_CHKSUM = "file://sd_fusing.sh;md5=9343188afe21ccc8e061d6f0fe9a8fd9;endline=10"

SRC_URI = " \
        http://www.mdrjr.net/odroid/mirror/BSPs/Alpha4/unpacked/boot.tar.gz \
        file://install-on-sdcard.sh \
        "

SRC_URI[md5sum] = "7a7e094771f52314a232e56d753b7f83"
SRC_URI[sha256sum] = "e0db737d9e49f937425e4778b0ab892623bcc389d7c26329ba2e97ae7bb475c4"

S = "${WORKDIR}/boot"

do_deploy () {
    install -d ${DEPLOYDIR}
    cp -v *bl1* ${DEPLOYDIR}/BL1.bin
    cp -v *bl2* ${DEPLOYDIR}/BL2.bin
    cp -v u-boot.bin ${DEPLOYDIR}/u-boot.bin
    cp -v *tzsw*.bin ${DEPLOYDIR}/TZSW.bin
    cp -v ${WORKDIR}/install-on-sdcard.sh ${DEPLOYDIR}
}

addtask deploy
