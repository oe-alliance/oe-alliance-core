PR .= ".1"

FILESEXTRAPATHS_prepend := "${THISDIR}/${P}:"

SRC_URI += "file://strace-0001-sigaction-wrap-sa_restorer-in-ifdef-SA_RESTORER-cons.patch"