FILESEXTRAPATHS_append := "${THISDIR}/files"
SRC_URI_append = " file://0001-Add-LLMNR-support.patch \
                   file://0002-Fix-LLMNR-response.patch"

PR = "r1"
