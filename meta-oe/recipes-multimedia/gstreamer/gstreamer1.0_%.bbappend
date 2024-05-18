FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGE_NO_LOCALE = "1"
SRC_URI:append = " file://0001-revert-use-new-gst-adapter-get-buffer.patch"

PV = "1.22.12"
 
SRC_URI[md5sum] = "df403dd4a5ef97add43c3dfa4d4b50f9"
SRC_URI[sha256sum] = "ac352f3d02caa67f3b169daa9aa78b04dea0fc08a727de73cb28d89bd54c6f61"
