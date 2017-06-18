DESCRIPTION = "STM ST-231 Coprocessor firmware"
LICENSE = "CLOSED"
SECTION = "base"
inherit allarch
require conf/license/license-close.inc

# fix architecture mismatch QA error
INSANE_SKIP_${PN} = "arch"

PR = "r7"

BINARY_STSLAVE_FW_PATH ?= "/data/stslave_fw"

SRC_URI = "file://${BINARY_STSLAVE_FW_PATH}/${MACHINE}/audio.elf \
    file://${BINARY_STSLAVE_FW_PATH}/${MACHINE}/video.elf \
"

FILES_${PN} += "/boot"

do_install () {
    install -d ${D}/boot
    install -m 644 ${BINARY_STSLAVE_FW_PATH}/${MACHINE}/audio.elf  ${D}/boot
    install -m 644 ${BINARY_STSLAVE_FW_PATH}/${MACHINE}/video.elf  ${D}/boot
    # Remove stuff from old packages if present
    if [ -e /etc/init.d/stslave.sh ] ; then
        rm /etc/init.d/stslave.sh
    fi
    if [ -e /etc/rcS.d/S03stslave ] ; then
        rm /etc/rcS.d/S03stslave
    fi
}

