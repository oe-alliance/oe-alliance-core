SUMMARY = "update static ubi volume"
SECTION = "console/utils"

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://updateubivolume.cpp;firstline=2;endline=13;md5=c93a0d6160b7f4ce64067b5326cce929"

PV = "1.0"
PR = "r0"

S = "${WORKDIR}"

SRC_URI = "file://updateubivolume.cpp"

do_compile() {
    ${CXX} -o updateubivolume updateubivolume.cpp
}

do_install() {
    install -d ${D}/${bindir}
    install ${S}/updateubivolume ${D}/${bindir}
}
