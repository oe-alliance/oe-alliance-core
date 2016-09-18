PR .= ".1"

SRC_URI += "file://sanity-check-provides.patch \
    file://0001-reuse-the-installed_files-list-when-possible.patch \
    file://stop_deprecated_version_message.patch \
    file://0.3.1_make_insane_patches_nonfatal.patch \
    file://0.3.1_busybox_workaround.patch \
    file://modprobe \
    "

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

do_install_prepend() {
    install -d ${D}${datadir}/opkg/intercept
    install -m 755 ${WORKDIR}/modprobe ${D}${datadir}/opkg/intercept/
}
