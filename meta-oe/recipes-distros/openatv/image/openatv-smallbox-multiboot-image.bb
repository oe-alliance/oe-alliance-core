SUMMARY = "OpenATV full Enigma2 image for SmallBox Chkroot Multiboot"

# This transport image is an OpenATV-only companion to oea-image. Other
# OE-Alliance distributions keep their existing SmallFlash image paths.
COMPATIBLE_MACHINE = "${@'.*' if d.getVar('DISTRO') == 'openatv' else '^$'}"

DEPENDS += "packagegroup-oea-feed-${DISTRO} zip-native"

require ../../../recipes-oe-alliance/image/oea-image-distro.inc

# Chkroot runs from external storage and is not constrained by the receiver's
# internal flash. Include the packages which the FlashExpander path normally
# installs later through the packagegroup-openatv-small compatibility name.
IMAGE_INSTALL:append = " packagegroup-oea-smallflash-openatv ofgwrite"

# Keep every intermediate artifact separate from the small internal bootstrap
# image. This image intentionally contains the complete Enigma2 stack.
export IMAGE_BASENAME = "${DISTRO}-smallbox-multiboot"
IMAGE_NAME = "${DISTRO_NAME}-${IMAGE_VERSION}-${MACHINEBUILD}-${DATE}-smallbox-rootfs"
SMALLBOX_MULTIBOOT_ARCHIVE = "${DISTRO_NAME}-${IMAGE_VERSION}-${MACHINEBUILD}-${DATE}_multiboot.zip"
# OE-Alliance normally shares ${TMPDIR}/rootfs/${MACHINE} between image
# recipes. The bootstrap and full Chkroot image are deliberately built in one
# graph, so the full image needs a private root tree to avoid opkg/rootfs races.
IMAGE_ROOTFS = "${WORKDIR}/rootfs"
IMAGE_FSTYPES = "tar.bz2"

# This is a Chkroot transport image, not an image for the receiver's NAND.
# Suppress machine-specific tar packaging hooks so the generic rootfs tarball
# remains available for the rootfs-only archive below.
IMAGE_CMD:tar:prepend = ""
IMAGE_CMD:tar:append = ""

# This rootfs is the special SmallBox tar transport.  The normal machine-wide
# enigma.info keeps describing the receiver's native flash format (UBI, JFFS2,
# etc.).  Mark only this full Chkroot image and make its actual backup/flash
# format explicit.  ImageBackup reads these values from the selected slot,
# allowing ordinary Chkroot flash formats and SmallBox tar images to coexist.
smallbox_multiboot_boxinfo() {
	info="${IMAGE_ROOTFS}${libdir}/enigma.info"
	if [ ! -s "$info" ]; then
		bbfatal "SmallBox Multiboot rootfs has no enigma.info"
	fi

	sed -i \
		-e '/^imagefs=/d' \
		-e '/^rootfile=/d' \
		-e '/^smallboxmultiboot=/d' \
		-e '/^checksum=/d' \
		"$info"
	printf "imagefs='tar.bz2'\n" >> "$info"
	printf "rootfile='rootfs.tar.bz2'\n" >> "$info"
	printf "smallboxmultiboot=True\n" >> "$info"
	checksum="$(md5sum "$info" | awk '{print $1}')"
	printf 'checksum=%s\n' "$checksum" >> "$info"
}

ROOTFS_POSTPROCESS_COMMAND:append = " smallbox_multiboot_boxinfo;"

do_smallbox_multiboot_archive() {
	stage="${WORKDIR}/smallbox-multiboot-archive"
	archive="${DEPLOY_DIR_IMAGE}/${SMALLBOX_MULTIBOOT_ARCHIVE}"
	root_image=""

	rm -rf "$stage"
	install -d "$stage/smallbox/${MACHINEBUILD}"

	for candidate in \
		"${IMGDEPLOYDIR}/${IMAGE_NAME}.tar.bz2" \
		"${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.tar.bz2" \
		"${IMGDEPLOYDIR}/${IMAGE_NAME}.rootfs.tar.bz2" \
		"${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.tar.bz2"
	do
		if [ -s "$candidate" ]; then
			root_image="$candidate"
			break
		fi
	done

	if [ -z "$root_image" ] || [ ! -s "$root_image" ]; then
		bbfatal "No rootfs.tar.bz2 was produced for the SmallBox Multiboot archive"
	fi

	install -m 0644 "$root_image" \
		"$stage/smallbox/${MACHINEBUILD}/rootfs.tar.bz2"
	printf '%s\n' \
		"${DISTRO_NAME}-${DISTRO_VERSION}.${BUILD_VERSION}" \
		"SmallBox Chkroot Multiboot rootfs-only tar image" \
		> "$stage/smallbox/${MACHINEBUILD}/imageversion"

	rm -f "$archive" "$archive.sha256"
	cd "$stage"
	zip -q -r "$archive" smallbox

	# Only the rootfs-only Multiboot zip is a distributable image from this
	# recipe. Remove the intermediate tar and any stale machine-native USB zip
	# with the same IMAGE_NAME so it cannot be mistaken for a flash image.
	rm -f \
		"${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.tar.bz2" \
		"${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.tar.bz2" \
		"${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}_usb.zip" \
		"${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.testdata.json"
}

addtask smallbox_multiboot_archive after do_image_complete before do_build
