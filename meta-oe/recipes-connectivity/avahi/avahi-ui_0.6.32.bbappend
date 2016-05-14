FILESEXTRAPATHS_append := "${THISDIR}/files"
SRC_URI_append = " file://0001-Add-LLMNR-support.patch \
                   file://0002-Fix-LLMNR-response.patch"
PR = "r1"

AVAHI_GTK_append = " --enable-python"

FILES_python-avahi = "${libdir}/python2.7/site-packages/avahi/* ${libdir}/python2.7/site-packages/avahi_discover"

