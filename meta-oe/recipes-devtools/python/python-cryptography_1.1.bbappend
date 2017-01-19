FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR = "r4"

#SRC_URI += " \
#	file://cryptography-1.2.2-openssl-1.0.2g-mem_buf.patch \
#"

FILES_${PN}-dbg += " \
    ${libdir}/python2.7/site-packages/*.egg-info \
    "
