# OpenATV image hooks
MAINTAINER = "openATV team"

inherit openatv-image-info

# The SmallBox companion has its own rootfs and writes its own information.
# Do not let the internal image's prefix wildcard pick up that separate ZIP.
OPENATV_IMAGE_INFO_EXCLUDE = "${DISTRO_NAME}-${IMAGE_VERSION}-${MACHINEBUILD}-${DATE}_multiboot.zip"

# Apply the native SmallBox bootstrap only to OpenATV's main image recipe.
# Other OE-Alliance distributions retain their existing SmallFlash images.
OPENATV_SMALLBOX_BOOTSTRAP = "${@'1' if d.getVar('PN') == 'oea-image' and bb.utils.contains('MACHINE_FEATURES', 'smallflash', True, False, d) else '0'}"

OPENATV_SMALLBOX_DEFERRED_IMAGE_PACKAGES = " \
    packagegroup-oea-python-core \
    packagegroup-oea-multimedia \
    packagegroup-oea-gui \
    packagegroup-oea-enigma2-core \
    packagegroup-oea-enigma2-plugins \
    packagegroup-oea-distro-openatv \
    enigma2-plugin-settings-defaultsat \
"

IMAGE_INSTALL:remove = "${@d.getVar('OPENATV_SMALLBOX_DEFERRED_IMAGE_PACKAGES') if d.getVar('OPENATV_SMALLBOX_BOOTSTRAP') == '1' else ''}"
IMAGE_INSTALL:append = "${@' smallbox-wizard vsftpd busybox-telnetd' if d.getVar('OPENATV_SMALLBOX_BOOTSTRAP') == '1' else ''}"

# One OpenATV SmallBox build also creates a complete rootfs-only Chkroot
# archive. Package compilation is shared; only do_rootfs/do_image run again.
OPENATV_SMALLBOX_BUILD_CHKROOT = "${@'1' if d.getVar('OPENATV_SMALLBOX_BOOTSTRAP') == '1' and bb.utils.contains('MACHINE_FEATURES', 'chkrootmb', True, False, d) and 0 < int(d.getVar('FLASHSIZE') or '0') <= 256 else '0'}"
do_build[depends] += "${@'openatv-smallbox-multiboot-image:do_smallbox_multiboot_archive' if d.getVar('OPENATV_SMALLBOX_BUILD_CHKROOT') == '1' else ''}"

# Reject incomplete OpenATV SmallBox metadata early. FLASHSIZE is the OE image
# budget and may intentionally differ from the physical NAND size.
python __anonymous() {
    if d.getVar('OPENATV_SMALLBOX_BOOTSTRAP') != '1':
        return

    features = set((d.getVar('MACHINE_FEATURES') or '').split())
    raw_size = d.getVar('FLASHSIZE') or ''
    try:
        flash_size = int(raw_size)
    except ValueError:
        bb.fatal('%s: OpenATV SmallBox FLASHSIZE must be a positive integer, got %r' %
                 (d.getVar('MACHINE'), raw_size))
    if flash_size <= 0:
        bb.fatal('%s: OpenATV SmallBox FLASHSIZE must be greater than zero' %
                 d.getVar('MACHINE'))

    if 'smallbox-chkroot-required' in features:
        if 'chkrootmb' not in features:
            bb.fatal('%s: smallbox-chkroot-required also requires chkrootmb' %
                     d.getVar('MACHINE'))
        if flash_size > 256:
            bb.fatal('%s: required Chkroot would not produce a full image '
                     'because FLASHSIZE is greater than 256 MiB' %
                     d.getVar('MACHINE'))
}

# The bootstrap has no Enigma2 launcher until the wizard installs the full
# package set.  Point inittab at a wrapper which enforces setup even if an old
# receiver cannot run update-rc.d's deferred package post-installation.  This
# is deliberately restricted to OpenATV's SmallBox bootstrap image.
openatv_smallbox_install_gui_launcher() {
	if [ "${OPENATV_SMALLBOX_BOOTSTRAP}" != "1" ]; then
		return
	fi

	inittab="${IMAGE_ROOTFS}${sysconfdir}/inittab"
	if [ ! -f "$inittab" ]; then
		bbfatal "OpenATV SmallBox bootstrap has no /etc/inittab"
	fi
	if [ ! -x "${IMAGE_ROOTFS}${sbindir}/smallbox-wizard-launcher" ]; then
		bbfatal "OpenATV SmallBox bootstrap has no SmallBox GUI launcher"
	fi

	# Package post-install scripts may run on the first boot before the
	# volatile init script. Seed their targets in the immutable image so opkg,
	# cron and the wizard always have usable runtime directories.
	install -d -m 1777 "${IMAGE_ROOTFS}/var/volatile/tmp"
	install -d -m 0755 "${IMAGE_ROOTFS}/var/volatile/run"
	# tuxbox-links later owns /var/spool/cron as a symlink to /etc/cron.
	# Seed the symlink target, never the packaged path itself, otherwise opkg
	# cannot replace the bootstrap directory during the Enigma2 installation.
	install -d -m 0770 "${IMAGE_ROOTFS}/etc/cron/crontabs"
	if [ -L "${IMAGE_ROOTFS}/tmp" ]; then
		ln -snf /var/volatile/tmp "${IMAGE_ROOTFS}/tmp"
	elif [ -d "${IMAGE_ROOTFS}/tmp" ]; then
		chmod 1777 "${IMAGE_ROOTFS}/tmp"
	elif [ ! -e "${IMAGE_ROOTFS}/tmp" ]; then
		ln -s /var/volatile/tmp "${IMAGE_ROOTFS}/tmp"
	else
		bbfatal "OpenATV SmallBox bootstrap /tmp is neither a directory nor a symlink"
	fi

	sed -i 's#^gui:3:respawn:.*#gui:3:respawn:/usr/sbin/smallbox-wizard-launcher#' "$inittab"
}

ROOTFS_POSTPROCESS_COMMAND:append = " openatv_smallbox_install_gui_launcher;"

# Single-core receivers remain responsive once Enigma2 is running, but their
# sequential SysV boot is slowed down by optional discovery and file-server
# daemons.  Keep the packages available in the image/feed and let Enigma2's
# Network Services screen enable them when requested, while fresh images boot
# with a TV-focused service set.
openatv_singlecore_disable_optional_services() {
	if [ "${@bb.utils.contains('MACHINE_FEATURES', 'singlecore', '1', '0', d)}" != "1" ]; then
		return
	fi

	services="\
		autofs \
		avahi-daemon \
		llmnrd \
		nfsserver \
		samba \
		smartd \
		wsdd \
		wsdd2"

	# Keep remote support access available throughout SmallBox setup.  The
	# native wizard runs before Enigma2, so Enigma2 cannot recover disabled
	# FTP or Telnet services when first-boot setup fails.
	if [ "${@bb.utils.contains('MACHINE_FEATURES', 'smallflash', '1', '0', d)}" != "1" ]; then
		services="$services telnetd.busybox vsftpd"
	fi

	for runlevel in 2 3 4 5; do
		for service in $services; do
			rm -f "${IMAGE_ROOTFS}/etc/rc${runlevel}.d/S"*"${service}"
		done
	done
}

ROOTFS_POSTPROCESS_COMMAND:append = " openatv_singlecore_disable_optional_services;"
