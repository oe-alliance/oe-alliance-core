SUMMARY = "Create Azbox MiniME webinterface update image"
SECTION = "console/utils"
LICENSE = "CLOSED"

PROVIDES = "azbox-minime-packer"

PV="1.4"
PR = "r1"

SRC_URI = "file://pack_minime_image.c"

inherit native

S = "${WORKDIR}"

do_compile() {
    ${CC} -o pack_minime_image pack_minime_image.c
}

do_install() {
    install -d ${D}/${bindir}/
    install -m 0755 ${S}/pack_minime_image ${D}/${bindir}/
}