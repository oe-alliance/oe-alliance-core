SUMMARY = "Kodinerds Kodi add-on repository"
DESCRIPTION = "Repository descriptor for the German Kodinerds community add-on repository, including its Kodi 22 Piers feed."
HOMEPAGE = "https://repo.kodinerds.net/"

require conf/license/license-gplv2.inc

SRC_URI = "https://repo.kodinerds.net/addons/repository.kodinerds/repository.kodinerds-${PV}.zip"
SRC_URI[sha256sum] = "6adba4acc198cde6055926e4495565549f9f5f453db68f73389646223aadd412"

inherit allarch

S = "${UNPACKDIR}/repository.kodinerds"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -d ${D}${datadir}/kodi/addons/repository.kodinerds
    cp -R --no-preserve=ownership ${S}/. ${D}${datadir}/kodi/addons/repository.kodinerds/
}

FILES:${PN} = "${datadir}/kodi/addons/repository.kodinerds"
