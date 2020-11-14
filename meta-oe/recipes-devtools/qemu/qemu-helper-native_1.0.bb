SUMMARY = "Helper utilities needed by the runqemu script"
LICENSE = "GPLv2"
RDEPENDS_${PN} = "qemu-system-native"
PR = "r1"

LIC_FILES_CHKSUM = "file://${WORKDIR}/tunctl.c;endline=4;md5=5c51de126857f0b459affe89ace67e0d"

SRC_URI = "\
    file://tunctl.c \
    file://qemu-oe-bridge-helper \
    "

S = "${WORKDIR}"

inherit native

do_compile() {
	${CC} ${CFLAGS} ${LDFLAGS} -Wall tunctl.c -o tunctl
}

do_install() {
	install -d ${D}${bindir}
	install tunctl ${D}${bindir}/

    install -m 755 ${WORKDIR}/qemu-oe-bridge-helper ${D}${bindir}/
}

DEPENDS += "qemu-system-native"
addtask addto_recipe_sysroot after do_populate_sysroot before do_build
