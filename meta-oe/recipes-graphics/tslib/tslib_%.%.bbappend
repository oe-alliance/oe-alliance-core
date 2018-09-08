FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

BBCLASSEXTEND = "native"

PACKAGES_remove = "tslib-conf"

RDEPENDS_${PN}_remove = "tslib-conf"

SRC_URI_append = "\
    file://add_old_kernel_support.patch \
    file://disable_EVIOCGPROP_call_if_unavailable.patch \
"

FILES_${PN} =+ "${sysconfdir}/ts.conf ${sysconfdir}/profile.d/tslib.sh ${datadir}/tslib"
