#FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

#SRC_URI_sh4 += "file://fix-build-for-sh4.patch"

do_configure_prepend_sh4 (){
    sed -i -e /'-Werror=undef'/d ${S}/meson.build
}