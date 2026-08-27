# OpenATV image hooks
MAINTAINER = "openATV team"

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
IMAGE_INSTALL:append = "${@' smallbox-wizard' if d.getVar('OPENATV_SMALLBOX_BOOTSTRAP') == '1' else ''}"

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

# Single-core receivers remain responsive once Enigma2 is running, but their
# sequential SysV boot is slowed down by optional discovery and file-server
# daemons.  Keep the packages available in the image/feed and let Enigma2's
# Network Services screen enable them when requested, while fresh images boot
# with a TV-focused service set.
openatv_singlecore_disable_optional_services() {
	if [ "${@bb.utils.contains('MACHINE_FEATURES', 'singlecore', '1', '0', d)}" != "1" ]; then
		return
	fi

	for runlevel in 2 3 4 5; do
		for service in \
			autofs \
			avahi-daemon \
			llmnrd \
			nfsserver \
			samba \
			smartd \
			telnetd.busybox \
			vsftpd \
			wsdd \
			wsdd2
		do
			rm -f "${IMAGE_ROOTFS}/etc/rc${runlevel}.d/S"*"${service}"
		done
	done
}

ROOTFS_POSTPROCESS_COMMAND:append = " openatv_singlecore_disable_optional_services;"
