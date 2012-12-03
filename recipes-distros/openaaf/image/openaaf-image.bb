DESCRIPTION = "OpenAAF Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "openAAF team"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "r${DATETIME}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

IMAGE_INSTALL = "openaaf-base \
				${@base_contains("MACHINE_FEATURES", "smallflash", "", \
				" \
				task-base-smbfs-client \
				task-base-smbfs \
				task-base-nfs \
				", d)} \
				"

export IMAGE_BASENAME = "openaaf-image"
IMAGE_LINGUAS = ""

IMAGE_FEATURES += "package-management"

inherit image
