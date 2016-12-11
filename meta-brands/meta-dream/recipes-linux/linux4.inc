DESCRIPTION = "Linux Kernel"
SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit kernel siteinfo

CMDLINE ?= ""
LOCALVERSION ?= ""

#kernel_conf_variable CMDLINE "\"${CMDLINE} ${CMDLINE_DEBUG}\""
kernel_conf_variable() {
    CONF_SED_SCRIPT="$CONF_SED_SCRIPT /CONFIG_$1[ =]/d;"
    if test "$2" = "n"
    then
        echo "# CONFIG_$1 is not set" >> ${B}/.config
    else
        echo "CONFIG_$1=$2" >> ${B}/.config
    fi
}

DEFCONFIG_IN_SRC_URI = "${@bb.utils.contains('SRC_URI', 'file://defconfig', 'true', 'false', d)}"

do_configure_prepend() {
    if [ -n "${DEFCONFIG}" ] && ! ${DEFCONFIG_IN_SRC_URI}; then
        yes '' | oe_runmake -C ${S} O=${B} ${DEFCONFIG}_defconfig
        mv ${B}/.config ${WORKDIR}/defconfig
    fi

    echo "" > ${B}/.config
    CONF_SED_SCRIPT=""

    if [ -n "${CMDLINE}" ]; then
        kernel_conf_variable CMDLINE "\"${CMDLINE}\""
    fi

    if [ -n "${LOCALVERSION}" ]; then
        kernel_conf_variable LOCALVERSION "\"${LOCALVERSION}\""
    fi
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

    sed -e "${CONF_SED_SCRIPT}" \
    < '${WORKDIR}/defconfig' >>'${B}/.config'

    yes '' | oe_runmake -C ${S} O=${B} oldconfig
}

do_configure_append() {
    if test -e scripts/Makefile.fwinst ; then
        sed -i -e "s:-m0644:-m 0644:g" scripts/Makefile.fwinst
    fi
}

do_install_append() {
    oe_runmake headers_install INSTALL_HDR_PATH=${D}${exec_prefix}/src/linux-${KERNEL_VERSION} ARCH=$ARCH
}

PACKAGES =+ "kernel-headers"
FILES_kernel-headers = "${exec_prefix}/src/linux*"

KERNEL_MODULE_AUTOLOAD += "nfsd"
