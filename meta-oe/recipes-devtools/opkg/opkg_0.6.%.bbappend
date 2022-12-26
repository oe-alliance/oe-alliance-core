
PACKAGECONFIG ??= ""

SRC_URI += " \
    file://0000-Revert-libopkg-track-the-number-of-packages-installi.patch \
    file://0001-sanity-check-provides.patch \
    file://0002-stop_deprecated_version_message.patch \
    file://0003-make_insane_checks_nonfatal.patch \
    file://0004-busybox_workaround.patch \
    file://0005-symlinks-can-be-valid-directories-too.patch \
    file://0006-reuse-the-installed_files-list-when-possible.patch \
    file://modprobe \
    "

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

do_install:prepend() {
    install -d ${D}${datadir}/opkg/intercept
    install -m 755 ${WORKDIR}/modprobe ${D}${datadir}/opkg/intercept/
}

PR = "r6"
