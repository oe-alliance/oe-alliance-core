FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR = "r3"

FILES_${PN}-dbg += " \
    ${libdir}/python2.7/site-packages/*.egg-info \
    "
