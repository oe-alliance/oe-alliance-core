FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# linuxtv.org has taken the hg archive down, upstream only survives through the
# Yocto source mirror. Fetch from our own mirror instead.
SRC_URI:remove = "https://www.linuxtv.org/hg/dvb-apps/archive/3d43b280298c.tar.bz2;downloadfilename=${BPN}-3d43b280298c.tar.bz2"
SRC_URI:prepend = "https://source.mynonpublic.com/3d43b280298c.tar.bz2;downloadfilename=${BPN}-3d43b280298c.tar.bz2 "

SRC_URI:append = " \
            file://0007-util-DVBC_ANNEX_AC.patch \
            file://0008-util-add-defines-for-old-kernels.patch \
"
