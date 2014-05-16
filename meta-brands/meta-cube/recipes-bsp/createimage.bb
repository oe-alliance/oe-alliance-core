SUMMARY = "create image file"
SECTION = "console/utils"
PACKAGE_ARCH = "${MACHINEBUILD}"

PN = "createimage-${MACHINE}"
PV = "1.0"
PR = "r3"

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://createimage.c;firstline=2;endline=13;md5=c93a0d6160b7f4ce64067b5326cce929"

SRC_URI = "file://createimage.c"

inherit native

S = "${WORKDIR}"

do_compile() {
    ${CC} -DMAGIC=${CREATEIMAGE_MAGIC} -o createimage_${MACHINE} createimage.c
}

do_install() {
    install -d ${D}/${bindir}/
    install -m 0755 ${S}/createimage_${MACHINE} ${D}/${bindir}/
}
