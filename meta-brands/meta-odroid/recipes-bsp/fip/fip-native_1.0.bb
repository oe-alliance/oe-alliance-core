SUMMARY = "Arm Trusted Firmware"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://fip_create.c;md5=e4fd674944d5086aea300d668e6a8500"

SRC_URI = " \
	file://fip_create.c \
	file://fip_create.h \
	file://firmware_image_package.h \
	file://Makefile \
	file://uuid.h \
"

S = "${WORKDIR}/${PN}"

export CROSS_COMPILE = "${TARGET_PREFIX}"

CFLAGS[unexport] = "1"
LDFLAGS[unexport] = "1"
AS[unexport] = "1"
LD[unexport] = "1"

inherit native

do_configure[noexec] = "1"

do_compile () {
	cp ${WORKDIR}/*.c ${S}/.
	cp ${WORKDIR}/*.h ${S}/.
	cp ${WORKDIR}/Makefile ${S}/.
	oe_runmake 
}

do_install () {
	install -d ${D}/${sbindir}
	install -m 775 ${S}/fip_create ${D}/${sbindir}
}
