FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://fix-build-openssl102q.patch"


do_configure_append() {
        echo "# Simultaneous Authentication of Equals (SAE), WPA3-Personal" >> ${B}/.config
        echo "CONFIG_SAE=y" >> ${B}/.config
}