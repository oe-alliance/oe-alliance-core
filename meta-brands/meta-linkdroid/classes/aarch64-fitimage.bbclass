inherit kernel-uboot

python __anonymous () {
    kerneltype = d.getVar('KERNEL_IMAGETYPE', True)
    if kerneltype == 'fitImage':
        depends = d.getVar("DEPENDS", True)
        depends = "%s u-boot-mkimage-native dtc-native" % depends
        d.setVar("DEPENDS", depends)
        d.setVar("KERNEL_IMAGETYPE_FOR_MAKE", "Image")
        d.setVar("KERNEL_OUTPUT", "arch/${ARCH}/boot/Image")
        d.setVar("linux_comp", "none")

        image = d.getVar('INITRAMFS_IMAGE', True)
        if image:
#            d.appendVarFlag('do_assemble_fitimage_initramfs', 'depends', ' ${INITRAMFS_IMAGE}:do_image_complete')
            d.appendVarFlag('do_assemble_fitimage_initramfs', 'depends', ' ${INITRAMFS_IMAGE}:do_image_complete')

}

UBOOT_MKIMAGE_DTCOPTS ??= ""

fitimage_emit_fit_header() {
	cat << EOF >> ${1}
/dts-v1/;

/ {
        description = "U-Boot fitImage for ${DISTRO_NAME}/${PV}/${MACHINE}";
        #address-cells = <1>;
EOF
}

fitimage_emit_section_maint() {
	case $2 in
	imagestart)
		cat << EOF >> ${1}

        images {
EOF
	;;
	confstart)
		cat << EOF >> ${1}

        configurations {
EOF
	;;
	sectend)
		cat << EOF >> ${1}
	};
EOF
	;;
	fitend)
		cat << EOF >> ${1}
};
EOF
	;;
	esac
}

fitimage_emit_section_kernel() {

	kernel_csum="sha1"

	ENTRYPOINT=${UBOOT_ENTRYPOINT}
	if test -n "${UBOOT_ENTRYSYMBOL}"; then
		ENTRYPOINT=`${HOST_PREFIX}nm ${S}/vmlinux | \
			awk '$4=="${UBOOT_ENTRYSYMBOL}" {print $2}'`
	fi

	cat << EOF >> ${1}
                kernel@${2} {
                        description = "Linux kernel";
                        data = /incbin/("${3}");
                        type = "kernel";
                        arch = "${UBOOT_ARCH}";
                        os = "linux";
                        compression = "${4}";
                        load = <${UBOOT_LOADADDRESS}>;
                        entry = <${ENTRYPOINT}>;
                        hash@1 {
                                algo = "${kernel_csum}";
                        };
                };
EOF
}

fitimage_emit_section_dtb() {

	dtb_csum="sha1"

	cat << EOF >> ${1}
                fdt@${2} {
                        description = "Flattened Device Tree blob";
                        data = /incbin/("${3}");
                        type = "flat_dt";
                        arch = "${UBOOT_ARCH}";
                        compression = "none";
                        hash@1 {
                                algo = "${dtb_csum}";
                        };
                };
EOF
}

fitimage_emit_section_ramdisk() {

	ramdisk_csum="sha1"

	cat << EOF >> ${1}
                ramdisk@${2} {
                        description = "ramdisk image";
                        data = /incbin/("${3}");
                        type = "ramdisk";
                        arch = "${UBOOT_ARCH}";
                        os = "linux";
                        compression = "none";
                        hash@1 {
                                algo = "${ramdisk_csum}";
                        };
                };
EOF
}

fitimage_emit_section_config() {

	conf_csum="sha1"
	# Test if we have any DTBs at all
	if [ -z "${3}" -a -z "${4}" ] ; then
		conf_desc="Boot Linux kernel"
		fdt_line=""
		ramdisk_line=""
	elif [ -z "${4}" ]; then
		conf_desc="Boot Linux kernel with FDT blob"
		fdt_line="fdt = \"fdt@${3}\";"
		ramdisk_line=""
	elif [ -z "${3}" ]; then
		conf_desc="Boot Linux kernel with ramdisk"
		fdt_line=""
		ramdisk_line="ramdisk = \"ramdisk@${4}\";"
	else
		conf_desc="Boot Linux kernel with FDT blob, ramdisk"
		fdt_line="fdt = \"fdt@${3}\";"
		ramdisk_line="ramdisk = \"ramdisk@${4}\";"
	fi
	kernel_line="kernel = \"kernel@${2}\";"

	cat << EOF >> ${1}
                default = "conf@1";
                conf@1 {
                        description = "${conf_desc}";
			${kernel_line}
			${fdt_line}
			${ramdisk_line}
                        hash@1 {
                                algo = "${conf_csum}";
                        };
EOF

	cat << EOF >> ${1}
                };
EOF
}

fitimage_assemble() {
	kernelcount=1
	dtbcount=""
	ramdiskcount=${3}
	rm -f ${1} arch/${ARCH}/boot/${2}
	fitimage_emit_fit_header ${1}

	fitimage_emit_section_maint ${1} imagestart

	uboot_prep_kimage

	fitimage_emit_section_kernel ${1} "${kernelcount}" linux.bin "${linux_comp}"

	if test -n "${KERNEL_DEVICETREE}"; then
		dtbcount=1
		for DTB in ${KERNEL_DEVICETREE}; do
			if echo ${DTB} | grep -q '/dts/'; then
				bbwarn "${DTB} contains the full path to the the dts file, but only the dtb name should be used."
				DTB=`basename ${DTB} | sed 's,\.dts$,.dtb,g'`
			fi
			DTB_PATH="arch/${ARCH}/boot/dts/${DTB}"
			if [ ! -e "${DTB_PATH}" ]; then
				DTB_PATH="arch/${ARCH}/boot/${DTB}"
			fi

			fitimage_emit_section_dtb ${1} ${dtbcount} ${DTB_PATH}
			dtbcount=`expr ${dtbcount} + 1`
		done
	fi
	if [ "x${ramdiskcount}" = "x1" ] ; then
		copy_initramfs
		fitimage_emit_section_ramdisk ${1} "${ramdiskcount}" usr/${INITRAMFS_IMAGE}-${MACHINE}.cpio
	fi

	fitimage_emit_section_maint ${1} sectend

	kernelcount=1
	dtbcount=1

	fitimage_emit_section_maint ${1} confstart

	fitimage_emit_section_config ${1} ${kernelcount} ${dtbcount} ${ramdiskcount}

	fitimage_emit_section_maint ${1} sectend

	fitimage_emit_section_maint ${1} fitend
	if [ ! -e "${DEPLOY_DIR_IMAGE}/${INITRAMFS_IMAGE}-${MACHINE}.rootfs.cpio.gz" ]; then
#    ln -s "${DEPLOY_DIR_IMAGE}/${INITRAMFS_IMAGE}-${MACHINE}.cpio.gz"  "${DEPLOY_DIR_IMAGE}/${INITRAMFS_IMAGE}-${MACHINE}.rootfs.cpio.gz" 
		mkdir -p "${DEPLOY_DIR_IMAGE}"
		ln -sf "${DEPLOY_DIR_IMAGE}/${INITRAMFS_IMAGE}-${MACHINE}.rootfs.cpio.gz"  "${DEPLOY_DIR_IMAGE}/${INITRAMFS_IMAGE}-${MACHINE}.cpio.gz" 
	fi
	uboot-mkimage \
		${@'-D "${UBOOT_MKIMAGE_DTCOPTS}"' if len('${UBOOT_MKIMAGE_DTCOPTS}') else ''} \
		-f ${1} \
		arch/${ARCH}/boot/${2}

}

do_assemble_fitimage() {
	if echo ${KERNEL_IMAGETYPE} | grep -wq "fitImage"; then
		cd ${B}
		fitimage_assemble fit-image.its fitImage
	fi
}

addtask assemble_fitimage before do_install after do_compile

do_assemble_fitimage_initramfs() {
	if echo ${KERNEL_IMAGETYPE} | grep -wq "fitImage" && \
		test -n "${INITRAMFS_IMAGE}" ; then
		cd ${B}
		fitimage_assemble fit-image-${INITRAMFS_IMAGE}.its fitImage-${INITRAMFS_IMAGE} 1
	fi
}

addtask assemble_fitimage_initramfs before do_deploy after do_install


kernel_do_deploy[vardepsexclude] = "DATETIME"
kernel_do_deploy_append() {
	if test "x${KERNEL_IMAGETYPE}" = "xfitImage" ; then
		cd ${B}
		echo "Copying fit-image.its source file..."
		its_base_name="fitImage-its-${PV}-${PR}-${MACHINE}-${DATETIME}"
		its_symlink_name=fitImage-its-${MACHINE}
		install -m 0644 fit-image.its ${DEPLOYDIR}/${its_base_name}.its
		linux_bin_base_name="fitImage-linux.bin-${PV}-${PR}-${MACHINE}-${DATETIME}"
		linux_bin_symlink_name=fitImage-linux.bin-${MACHINE}
		install -m 0644 linux.bin ${DEPLOYDIR}/${linux_bin_base_name}.bin

		if [ -n "${INITRAMFS_IMAGE}" ]; then
			echo "Copying fit-image-${INITRAMFS_IMAGE}.its source file..."
			its_initramfs_base_name="${KERNEL_IMAGETYPE}-its-${INITRAMFS_IMAGE}-${PV}-${PR}-${MACHINE}-${DATETIME}"
			its_initramfs_symlink_name=${KERNEL_IMAGETYPE}-its-${INITRAMFS_IMAGE}-${MACHINE}
			install -m 0644 fit-image-${INITRAMFS_IMAGE}.its ${DEPLOYDIR}/${its_initramfs_base_name}.its
			fit_initramfs_base_name="${KERNEL_IMAGETYPE}-${INITRAMFS_IMAGE}-${PV}-${PR}-${MACHINE}-${DATETIME}"
			fit_initramfs_symlink_name=${KERNEL_IMAGETYPE}-${INITRAMFS_IMAGE}-${MACHINE}
			install -m 0644 arch/${ARCH}/boot/fitImage-${INITRAMFS_IMAGE} ${DEPLOYDIR}/${fit_initramfs_base_name}.bin
		fi

		cd ${DEPLOYDIR}
		ln -sf ${its_base_name}.its ${its_symlink_name}.its
		ln -sf ${linux_bin_base_name}.bin ${linux_bin_symlink_name}.bin

		if [ -n "${INITRAMFS_IMAGE}" ]; then
			ln -sf ${its_initramfs_base_name}.its ${its_initramfs_symlink_name}.its
			ln -sf ${fit_initramfs_base_name}.bin ${fit_initramfs_symlink_name}.bin
		fi
	fi
}

