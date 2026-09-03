SUMMARY = "Kodinerds Kodi add-on repository"
DESCRIPTION = "Repository descriptor for the German Kodinerds community add-on repository, including its Kodi 22 Piers feed."
HOMEPAGE = "https://repo.kodinerds.net/"

require conf/license/license-gplv2.inc

SRC_URI = "https://repo.kodinerds.net/addons/repository.kodinerds/repository.kodinerds-${PV}.zip"
SRC_URI[sha256sum] = "cd486fe16dcbf77212c50f036634d2e2b3de16037cb04463b6a7a45aa7132ed9"

inherit allarch

S = "${UNPACKDIR}/repository.kodinerds"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -d ${D}${datadir}/kodi/addons/repository.kodinerds
    cp -R --no-preserve=ownership ${S}/. ${D}${datadir}/kodi/addons/repository.kodinerds/
}

FILES:${PN} = "${datadir}/kodi/addons/repository.kodinerds"
