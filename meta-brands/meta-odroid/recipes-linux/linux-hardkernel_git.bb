DESCRIPTION = "Linux kernel for the Hardkernel ODROID devices"
SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

DEPENDS = "lzop-native"

# Mark archs/machines that this kernel supports
COMPATIBLE_MACHINE = "(odroid-u2|odroid-c1)"

inherit kernel siteinfo

# For device tree
require recipes-kernel/linux/linux-dtb.inc

# from where to fetch the kernel
KERNEL_REPO_OWNER ??= "hardkernel"
KERNEL_REPO_URI ??= "git://github.com/${KERNEL_REPO_OWNER}/linux.git"
KBRANCH ?= "odroid-3.8.y"
KBRANCH_odroid-c1 ?= "odroidc-3.10.y"

SRC_URI = " \
  ${KERNEL_REPO_URI};branch=${KBRANCH} \
  file://defconfig \
"

S = "${WORKDIR}/git/"

SRCREV = "${AUTOREV}"
SRCREV_odroid-c1 = "1d4f9e928a1c56a977bbf2d08dacc73a4161b5ff"

KV = "3.8.13"
KV_odroid-c1 = "3.10.96"
PV = "${KV}+gitr${SRCPV}"
LOCALVERSION ?= "odroid"


# stolen from meta-oe's linux.inc
#kernel_conf_variable CMDLINE "\"${CMDLINE} ${CMDLINE_DEBUG}\""
kernel_conf_variable() {
    CONF_SED_SCRIPT="$CONF_SED_SCRIPT /CONFIG_$1[ =]/d;"
    if test "$2" = "n"
    then
        echo "# CONFIG_$1 is not set" >> ${S}/.config
    else
        echo "CONFIG_$1=$2" >> ${S}/.config
    fi
}

do_configure_prepend_odroid-u2() {
     yes '' | oe_runmake odroidu2_defconfig
    CONF_SED_SCRIPT=""

    #
    # oabi / eabi support
    #
    kernel_conf_variable AEABI y
    if [ "${ARM_KEEP_OABI}" = "1" ] ; then
        kernel_conf_variable OABI_COMPAT y
    else
        kernel_conf_variable OABI_COMPAT n
    fi

    # When enabling thumb for userspace we also need thumb support in the kernel
    if [ "${ARM_INSTRUCTION_SET}" = "thumb" ] ; then
    kernel_conf_variable ARM_THUMB y
    fi
    kernel_conf_variable CMDLINE "\"${CMDLINE} ${CMDLINE_DEBUG}\""

    kernel_conf_variable LOCALVERSION "\"${LOCALVERSION}\""
    kernel_conf_variable LOCALVERSION_AUTO n

    kernel_conf_variable SYSFS_DEPRECATED n
    kernel_conf_variable SYSFS_DEPRECATED_V2 n
    kernel_conf_variable HOTPLUG y
    kernel_conf_variable UEVENT_HELPER_PATH \"\"
    kernel_conf_variable UNIX y
    kernel_conf_variable SYSFS y
    kernel_conf_variable PROC_FS y
    kernel_conf_variable TMPFS y
    kernel_conf_variable INOTIFY_USER y
    kernel_conf_variable SIGNALFD y
    kernel_conf_variable TMPFS_POSIX_ACL y
    kernel_conf_variable BLK_DEV_BSG y
    kernel_conf_variable DEVTMPFS y
    kernel_conf_variable DEVTMPFS_MOUNT y

    # Newer inits like systemd need cgroup support
    if [ "${KERNEL_ENABLE_CGROUPS}" = "1" ] ; then
        kernel_conf_variable CGROUP_SCHED y
        kernel_conf_variable CGROUPS y
        kernel_conf_variable CGROUP_NS y
        kernel_conf_variable CGROUP_FREEZER y
        kernel_conf_variable CGROUP_DEVICE y
        kernel_conf_variable CPUSETS y
        kernel_conf_variable PROC_PID_CPUSET y
        kernel_conf_variable CGROUP_CPUACCT y
        kernel_conf_variable RESOURCE_COUNTERS y
    fi

    #
    # root-over-nfs-over-usb-eth support. Limited, but should cover some cases.
    # Enable this by setting a proper CMDLINE_NFSROOT_USB.
    #
    if [ ! -z "${CMDLINE_NFSROOT_USB}" ]; then
        bbnote "Configuring the kernel for root-over-nfs-over-usb-eth with CMDLINE ${CMDLINE_NFSROOT_USB}"
        kernel_conf_variable INET y
        kernel_conf_variable IP_PNP y
        kernel_conf_variable USB_GADGET y
        kernel_conf_variable USB_GADGET_SELECTED y
        kernel_conf_variable USB_ETH y
        kernel_conf_variable NFS_FS y
        kernel_conf_variable ROOT_NFS y
        kernel_conf_variable CMDLINE \"${CMDLINE_NFSROOT_USB} ${CMDLINE_DEBUG}\"
    fi

    # edit inline, as we base on the defconfig provided by hardkernel repo
    sed -i -e "${CONF_SED_SCRIPT}" '${S}/.config'
    yes '' | oe_runmake oldconfig
}

do_install_append_odroid-u2() {
    # Helper script provided by Mauro Ribeiro
    tools/hardkernel/genBscr.sh
    oe_runmake headers_install INSTALL_HDR_PATH=${D}${exec_prefix}/src/linux-${KERNEL_VERSION} ARCH=$ARCH
}

do_deploy_append_odroid-u2() {
    cp -v *.scr ${DEPLOYDIR}
}

PACKAGES =+ "kernel-dbg kernel-headers"
FILES_kernel-dbg =+ " \
        ${exec_prefix}/src/kernel/drivers/amlogic/*/.debug \
        ${exec_prefix}/src/kernel/drivers/amlogic/*/*/.debug \
        ${exec_prefix}/src/kernel/drivers/amlogic/*/*/*/.debug \
"
FILES_kernel-headers = "${exec_prefix}/src/linux*"
