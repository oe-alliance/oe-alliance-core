PACKAGE_ARCH = "${MACHINEBUILD}"

PR_append = ".5"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
FILESEXTRAPATHS_prepend := "${THISDIR}/${DISTRO_NAME}:"
FILESEXTRAPATHS_prepend := "${THISDIR}/${MACHINE}:"
FILESEXTRAPATHS_prepend := "${THISDIR}/${MACHINEBUILD}:"

SRC_URI += "file://editor.sh"
SRC_URI += "file://locale.sh"
SRC_URI += "file://terminfo.sh"

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
    install -d ${D}${sysconfdir}/profile.d
    install -m 0644 ${WORKDIR}/editor.sh   ${D}${sysconfdir}/profile.d/editor.sh
    install -m 0644 ${WORKDIR}/locale.sh   ${D}${sysconfdir}/profile.d/locale.sh
    install -m 0644 ${WORKDIR}/terminfo.sh ${D}${sysconfdir}/profile.d/terminfo.sh
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

CONFFILES_${PN} += "${sysconfdir}/profile.d/locale.sh"
