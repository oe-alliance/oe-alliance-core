
PACKAGECONFIG ??= ""

SRC_URI += "file://sanity-check-provides.patch \
    file://0001-reuse-the-installed_files-list-when-possible.patch \
    file://stop_deprecated_version_message.patch \
    file://0.3.3_make_insane_checks_nonfatal.patch \
    file://0.3.3_busybox_workaround.patch \
    file://modprobe \
    file://0001-Revert-opkg_install-Only-allow-identical-symlinks-if.patch \
    file://0002-Revert-opkg_install-Allow-packages-to-provide-the-sa.patch \
    file://0003-Relax_checks_for_existing_dirs_to_allow_symlinks_too.patch \
    "

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

do_install_prepend() {
    install -d ${D}${datadir}/opkg/intercept
    install -m 755 ${WORKDIR}/modprobe ${D}${datadir}/opkg/intercept/
}

PR="r1"
