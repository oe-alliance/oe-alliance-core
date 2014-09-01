DESCRIPTION = "STM ST-231 Coprocessor firmware"
LICENSE = "CLOSED"
SECTION = "base"
PACKAGE_ARCH = "all"

# fix architecture mismatch QA error
INSANE_SKIP_${PN} = "arch"

PR = "r6"

BINARY_STSLAVE_FW_PATH ?= "/data/stslave_fw"

SRC_URI = "file://${BINARY_STSLAVE_FW_PATH}/${MACHINE}/audio.elf \
           file://${BINARY_STSLAVE_FW_PATH}/${MACHINE}/video.elf \
           file://stslave.sh \
"


FILES_${PN} += "/boot"

do_install () {
        install -d ${D}/boot
        install -d ${D}/${sysconfdir}/init.d
        install -d ${D}/${sysconfdir}/rcS.d
        install -m 0755 ${WORKDIR}/stslave.sh ${D}${sysconfdir}/init.d
        ln -sf ../init.d/stslave.sh ${D}${sysconfdir}/rcS.d/S03stslave 
        install -m 644 ${BINARY_STSLAVE_FW_PATH}/${MACHINE}/audio.elf  ${D}/boot
	install -m 644 ${BINARY_STSLAVE_FW_PATH}/${MACHINE}/video.elf  ${D}/boot
}


