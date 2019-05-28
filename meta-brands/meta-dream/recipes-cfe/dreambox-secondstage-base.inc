SUMMARY = "Dreambox second stage bootloader"
SECTION = "base"
LICENSE = "CLOSED"
PROVIDES += "virtual/bootloader"
RPROVIDES_${PN} += "dreambox-secondstage"
PRIORITY = "required"
INC_PR = "r22"
require conf/license/license-close.inc

do_install() {
	install -d ${D}/tmp
	buildimage --arch ${PRECOMPILED_ARCH} --raw ${EXTRA_BUILDCMD} \
	--erase-block-size ${DREAMBOX_ERASE_BLOCK_SIZE} \
	--sector-size ${DREAMBOX_SECTOR_SIZE} \
	--boot-partition=${DREAMBOX_PART0_SIZE}:secondstage-${PRECOMPILED_ARCH}-${PRECOMPILED_VERSION}.bin \
	> ${D}/tmp/secondstage-${PRECOMPILED_ARCH}.bin
}

PACKAGES = "${PN} ${PN}-bin"

PACKAGE_ARCH := "${MACHINE_ARCH}"

DEPENDS = "dreambox-buildimage-native"

do_configure[nostamp] = "1"

# busybox nandwrite requires oob patch and is not working in every box
RDEPENDS_${PN} = "mtd-utils"

FILES_${PN} = "/tmp/secondstage-${PRECOMPILED_ARCH}.bin"

pkg_postinst_${PN}() {
	if [ -z "$D" ] && grep -q '\<${PRECOMPILED_ARCH}\>' /proc/stb/info/model; then
		if [ -f /tmp/secondstage-${PRECOMPILED_ARCH}.bin ]; then
			flash_erase /dev/mtd1 0 0 2>/dev/null
			nandwrite -m -n -o /dev/mtd1 /tmp/secondstage-${PRECOMPILED_ARCH}.bin 2>/dev/null
			rm /tmp/secondstage-${PRECOMPILED_ARCH}.bin
		fi
	fi
}

do_deploy() {
	install -d ${DEPLOY_DIR_IMAGE}
	install -m 0644 ${S}/secondstage-${PRECOMPILED_ARCH}-${PRECOMPILED_VERSION}.bin ${DEPLOY_DIR_IMAGE}/secondstage-${MACHINE}.bin
}
addtask deploy before do_package after do_install
