DESCRIPTION = "Open4D Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "ViX team"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "r${DATETIME}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

IMAGE_INSTALL = " \
	oe-alliance-enigma2 \
	open4d-enigma2 \
	open4d-bootlogo \
	avahi-daemon \
	dropbear \
	early-configure \
	e2fsprogs-mke2fs \
	e2fsprogs-e2fsck \
	e2fsprogs-tune2fs \
	e2fsprogs-blkid \
	fakelocale \
	libavahi-client \
	ntpdate \
	opkg \
	sdparm \
	task-base \
	task-core-boot \
	tzdata \
	util-linux-sfdisk \
	volatile-media \
	vsftpd \
	"

export IMAGE_BASENAME = "open4d-image"
IMAGE_LINGUAS = ""

IMAGE_FEATURES += "package-management"

inherit image
