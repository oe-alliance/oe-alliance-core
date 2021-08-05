PACKAGE_ARCH = "${MACHINEBUILD}"

PR_append = ".5"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
FILESEXTRAPATHS_prepend := "${THISDIR}/${DISTRO_NAME}:"
FILESEXTRAPATHS_prepend := "${THISDIR}/${MACHINE}:"
FILESEXTRAPATHS_prepend := "${THISDIR}/${MACHINEBUILD}:"

SRC_URI += "file://editor.sh"
SRC_URI += "file://terminfo.sh"
SRC_URI += "file://mount-helper.sh"
SRC_URI += "file://filesystems"

hostname = "${MACHINEBUILD}"

do_install_append() {
    rm -rf ${D}/autofs
    rm -rf ${D}/mnt
    rm -rf ${D}/hdd
    ln -sf media/hdd ${D}/hdd
    ln -sf media ${D}/mnt
    rm -rf ${D}/media/*
    rm -fr ${D}/tmp
    mkdir ${D}/media/net
    install -d ${D}${sysconfdir}/udev
    install -m 0755 ${WORKDIR}/mount-helper.sh       ${D}${sysconfdir}/udev
    install -d ${D}${sysconfdir}/profile.d
    install -m 0644 ${WORKDIR}/editor.sh   ${D}${sysconfdir}/profile.d/editor.sh
    install -m 0644 ${WORKDIR}/terminfo.sh ${D}${sysconfdir}/profile.d/terminfo.sh
    install -m 0644 ${WORKDIR}/filesystems ${D}${sysconfdir}/filesystems

    # Inject machine specific blacklists into mount-helper:
    perl -i -pe 's:(\@BLACKLISTED\@):${MTD_BLACK}:s' ${D}${sysconfdir}/udev/mount-helper.sh

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

    if [ "${MACHINEBUILD}" = "azboxhd" ]; then
        printf "/dev/hda3\t\tswap\t\tswap\t\tdefaults\t\t\t\t0  0\n" >> ${D}${sysconfdir}/fstab
    fi
    if [ "${MACHINEBUILD}" = "sf4008" -o "${MACHINEBUILD}" = "sf5008" ]; then
        printf "/dev/mmcblk0p5\t\tnone\t\tswap\t\tsw\t\t\t\t\t0  0\n" >> ${D}${sysconfdir}/fstab
    fi
    if [ "${DISTRO}" = "egami" ]; then
        printf "/dev/sda1\t\t/media/hdd\tauto\t\tdefaults\t\t\t\t0  0\n" >> ${D}${sysconfdir}/fstab
    fi
}

# For Classic Dreambox Inject the /boot partition into /etc/fstab. At image creation time,
# this is done by IMAGE_CMD_ubi.nfi (image_types_nfi.bbclass).
pkg_postinst_${PN}_dreamboxv1() {
if [ -z "$D" ]; then
	ROOT='\<root=ubi0:rootfs\>'
	if grep -q $ROOT /proc/cmdline && ! grep -q '\s\+/boot\s\+' /etc/fstab; then
	       printf '/dev/mtdblock2\t/boot\t\tjffs2\tro\t\t\t\t0 0\n' >> /etc/fstab
	fi
	if grep -q '/dev/ubi0_1' /proc/mounts && ! grep -q '\s\+/data\s\+' /etc/fstab; then
	        printf '/dev/ubi0_1\t/data\t\tubifs\trw,nofail\t\t\t\t0 0\n' >> /etc/fstab
	fi
fi
}
