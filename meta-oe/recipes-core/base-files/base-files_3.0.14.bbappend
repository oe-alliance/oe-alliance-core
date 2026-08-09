PACKAGE_ARCH = "${MACHINEBUILD}"

# Trigger opkg update on the box when distro-versions repo bumps DISTRO_VERSION.
# Tilde (~) turns pre-release suffixes into a proper "older than release" marker
# for opkg's dpkg-style compare (e.g. 800~beta < 800).
PR = "r${@(d.getVar('DISTRO_VERSION') or 'nover').replace('-','~').replace('.','')}"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
FILESEXTRAPATHS:prepend := "${THISDIR}/${DISTRO_NAME}:"
FILESEXTRAPATHS:prepend := "${THISDIR}/${MACHINE}:"
FILESEXTRAPATHS:prepend := "${THISDIR}/${MACHINEBUILD}:"

SRC_URI += "file://editor.sh"
SRC_URI += "file://terminfo.sh"
SRC_URI += "file://filesystems"

hostname = "${MACHINEBUILD}"

# Detect rootfs filesystem type from IMAGE_FSTYPES (machine configs can override ROOTFS_FSTYPE / ROOTFS_MOUNTOPTS)
def get_rootfs_fstype(d):
    fstypes = (d.getVar('IMAGE_FSTYPES') or '').lower()
    mtd_rootfs = (d.getVar('MTD_ROOTFS') or '').lower()
    if 'ubi' in fstypes and 'emmc' not in fstypes and 'fastboot' not in fstypes:
        return 'ubifs'
    if 'jffs2' in fstypes:
        return 'jffs2'
    if 'emmc' in fstypes or 'fastboot' in fstypes or 'ext4' in fstypes or 'mmcblk' in mtd_rootfs:
        return 'ext4'
    return 'auto'

def get_rootfs_mountopts(d):
    fstype = get_rootfs_fstype(d)
    provider = d.getVar('PREFERRED_PROVIDER_virtual/kernel') or ''
    kver = (d.getVar('PREFERRED_VERSION_' + provider) or '').replace('%', '0')
    has_lazytime = bb.utils.vercmp_string_op(kver, '4.0', '>=') if kver else False
    if fstype == 'ext4':
        opts = 'defaults,noatime,commit=60'
        if has_lazytime:
            opts += ',lazytime'
        return opts
    if fstype == 'ubifs':
        return 'defaults,noatime,bulk_read'
    return 'defaults,noatime'

ROOTFS_FSTYPE ?= "${@get_rootfs_fstype(d)}"
ROOTFS_MOUNTOPTS ?= "${@get_rootfs_mountopts(d)}"

do_install:append() {
    rm -rf ${D}/autofs
    rm -rf ${D}/mnt
    rm -rf ${D}/hdd
    ln -sf media/hdd ${D}/hdd
    ln -sf media ${D}/mnt
    rm -rf ${D}/media/*
    rm -fr ${D}/tmp
    mkdir ${D}/media/net
    install -d ${D}${sysconfdir}/udev
    install -d ${D}${sysconfdir}/profile.d
    install -m 0644 ${S}/editor.sh   ${D}${sysconfdir}/profile.d/editor.sh
    install -m 0644 ${S}/terminfo.sh ${D}${sysconfdir}/profile.d/terminfo.sh
    install -m 0644 ${S}/filesystems ${D}${sysconfdir}/filesystems

    # For machines that should mount their boot partition, inject it (Set MTD_BOOTFS and MACHINE_FEATURES+="mountboot" in machine config!
    if ${@bb.utils.contains('MACHINE_FEATURES','mountboot','true','false',d)}; then
        export BOOTFS_BLOCK=$(echo -e ${MTD_BOOTFS} | perl -pe 's:(mtd)(\d):${1}block$2:') ; perl -i -pe 's:(\@rootfs\@):/dev/'${BOOTFS_BLOCK}'\t\t/boot\t\tauto\t\tdefaults\t\t\t\t1  1\n${1}:s' ${D}${sysconfdir}/fstab
    fi

    #if [ -n "${MTD_ROOTFS}" ]; then
    #    # Preferably mount rootfs by device rather than by label (Disabled here, it's for systemd branch) ...
    #    export ROOTFS_BLOCK=$(echo -e ${MTD_ROOTFS} | perl -pe 's:(mtd)(\d):${1}block$2:') ; perl -i -pe 's:\@rootfs\@:/dev/'${ROOTFS_BLOCK}':' ${D}${sysconfdir}/fstab
    #else
        # ... replace the place holder @rootfs@ by the verbatim label "rootfs" (plus one tab)
        perl -i -pe 's:(\@rootfs\@):rootfs\t:s' ${D}${sysconfdir}/fstab
    #fi

    # Optimize rootfs mount options based on storage type (ROOTFS_FSTYPE/ROOTFS_MOUNTOPTS)
    perl -i -pe 's/auto/${ROOTFS_FSTYPE}/ if /^rootfs/' ${D}${sysconfdir}/fstab
    perl -i -pe 's/defaults/${ROOTFS_MOUNTOPTS}/ if /^rootfs/' ${D}${sysconfdir}/fstab

    if [ "${MACHINEBUILD}" = "sf4008" ]; then
        printf "/dev/mmcblk0p5\t\tnone\t\tswap\t\tsw\t\t\t\t\t0  0\n" >> ${D}${sysconfdir}/fstab
    fi
    if [ "${MACHINEBUILD}" = "dreamone" -o "${MACHINEBUILD}" = "dreamtwo" ]; then
        mkdir ${D}/data
        printf '/dev/dreambox-data\t/data\t\tauto\tdefaults\t\t\t\t0 0\n' >> ${D}${sysconfdir}/fstab
    fi
    if [ "${MACHINEBUILD}" = "dm820" -o "${MACHINEBUILD}" = "dm7080" ]; then
        mkdir ${D}/data
        printf '/dev/mmcblk0p2\t/data\t\tauto\tdefaults\t\t\t\t0 0\n' >> ${D}${sysconfdir}/fstab
    fi
    if [ "${MACHINEBUILD}" = "dm900" -o "${MACHINEBUILD}" = "dm920" ]; then
        mkdir ${D}/data
        printf '/dev/mmcblk0p3\t/data\t\tauto\tdefaults\t\t\t\t0 0\n' >> ${D}${sysconfdir}/fstab
    fi
}

# For Classic Dreambox Inject the /boot partition into /etc/fstab. At image creation time,
# this is done by IMAGE_CMD:ubi.nfi (image_types_nfi.bbclass).
pkg_postinst:${PN}:dreamboxv1() {
if [ -z "$D" ]; then
	ROOT='\<root=ubi0:rootfs\>'
	if grep -q $ROOT /proc/cmdline && ! grep -q '\s\+/boot\s\+' /etc/fstab; then
	       printf '/dev/mtdblock2\t/boot\t\tjffs2\tro\t\t\t\t0 0\n' >> /etc/fstab
	fi
	if grep -q '/dev/ubi0_1' /proc/mounts && ! grep -q '\s\+/data\s\+' /etc/fstab; then
	        printf '/dev/ubi0_1\t/data\t\tubifs\tdefaults\t\t\t\t0 0\n' >> /etc/fstab
	fi
fi
}
