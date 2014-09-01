require linux-firmware.inc

SRC_URI = "ftp://ftp.stlinux.com/pub/stlinux/2.4/updates/RPMS/sh4/${STLINUX_FW_FILE_NAME} \
"

INC_PR = "r5"


S = "${WORKDIR}/opt/STM/STLinux-2.4/devkit/sh4/target"

# fix architecture mismatch QA error
INSANE_SKIP_${PN} = "arch"

do_unpack() {
	rpm2cpio.sh ${DL_DIR}/${STLINUX_FW_FILE_NAME} | cpio -di
}

