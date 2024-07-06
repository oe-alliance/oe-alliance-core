PR .= ".1"

kernel_do_install:append() {
    if [ ! -e ${B}/modules.builtin.modinfo ] ; then
        touch ${D}/${nonarch_base_libdir}/modules/${KERNEL_VERSION}/modules.builtin.modinfo
    fi
}

OLDEST_KERNEL = "3.2"
