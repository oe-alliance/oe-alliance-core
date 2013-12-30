DESCRIPTION = "Base packages require for image."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PV = "1.0"
PR = "r12"

inherit task

RDEPENDS = "\
	oe-alliance-base \
	openatv-enigma2 \
	openatv-bootlogo \
	openatv-spinner \
	libcrypto-compat \
	openssh-sftp-server \
	ntfs-3g \
	hddtemp \
	busybox-cron \
	python-imaging \
	ofgwrite \
	bash \
	"
