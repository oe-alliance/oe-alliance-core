
PACKAGECONFIG ??= ""

SRC_URI += "file://sanity-check-provides.patch \
    file://stop_deprecated_version_message.patch \
    file://0.3.3_make_insane_checks_nonfatal.patch \
    file://0.3.3_busybox_workaround.patch \
    file://modprobe \
    file://0002-symlinks-can-be-valid-directories-too.patch \
    "

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

do_install_prepend() {
    install -d ${D}${datadir}/opkg/intercept
    install -m 755 ${WORKDIR}/modprobe ${D}${datadir}/opkg/intercept/
}

PR="r3"
