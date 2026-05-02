FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PV = "2.54.0"
SRC_URI[tarball.sha256sum] = "45e8107643a44e3ce46f5665beb35af3932fb0d70017687905ab5d4e3aafa8eb"

SRC_URI += " \
    file://receive_timeout.patch \
    file://no_read_restart_on_eagain.patch \
"

SRC_URI:remove = "file://fixsort.patch"
