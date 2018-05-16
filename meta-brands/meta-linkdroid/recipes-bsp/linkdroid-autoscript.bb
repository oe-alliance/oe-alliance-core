SUMMARY = "Amlogic auto script generator"
SECTION = "bootloader"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit deploy

DEPENDS = "u-boot-mkimage-native"

SRC_URI = "file://aml_autoscript.txt"

S = "${WORKDIR}"

do_compile() {
	     uboot-mkimage -A arm -O linux -T script -C none -d aml_autoscript.txt aml_autoscript 
}

do_deploy() {
    install -d ${DEPLOYDIR}
    install -m 0644 aml_autoscript ${DEPLOYDIR}/
}

addtask deploy after do_install
ALLOW_EMPTY_${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"
