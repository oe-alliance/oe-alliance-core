FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

BBCLASSEXTEND = "native"

PACKAGES:remove = "tslib-conf"

RDEPENDS:${PN}:remove = "tslib-conf"

SRC_URI:append = " \
    file://disable_EVIOCGPROP_call_if_unavailable.patch \
"

FILES:${PN} =+ "${sysconfdir}/ts.conf ${sysconfdir}/profile.d/tslib.sh ${datadir}/tslib"
