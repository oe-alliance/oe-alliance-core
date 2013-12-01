PRINC = "5"
PACKAGE_ARCH = "${MACHINE_ARCH}"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_vuuno = " file://vuplus.patch"
SRC_URI_append_vusolo = " file://vuplus.patch"
SRC_URI_append_vusolo2 = " file://vuplus.patch"
SRC_URI_append_vuduo = " file://vuplus.patch"
SRC_URI_append_vuduo2 = " file://vuplus.patch"
SRC_URI_append_vuultimo = " file://vuplus.patch"
SRC_URI_append_tmtwin = " file://vuplus.patch"
SRC_URI_append_tm2t = " file://vuplus.patch"
SRC_URI_append_tmsingle = " file://vuplus.patch"
SRC_URI_append_tmnano = " file://vuplus.patch"
SRC_URI_append_tmnano2t = " file://vuplus.patch"
SRC_URI_append_iqonios100hd = " file://vuplus.patch"
SRC_URI_append_iqonios200hd = " file://vuplus.patch"
SRC_URI_append_iqonios300hd = " file://vuplus.patch"

SRC_URI[md5sum] = "3b2322695e9ee7bf447ebcdb85f93e83"
SRC_URI[sha256sum] = "639622180fe48dc7bb117ffbf263395d7ae47aac9819b8d9f83ff053ecf17bdd"

inherit autotools
