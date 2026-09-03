SUMMARY = "K.U.S AllInOne Kodi add-on repository"
DESCRIPTION = "Repository descriptor for the Kodi Unlimited Support AllInOne add-on repository."
HOMEPAGE = "https://kodi-unlimited-support.de/"

require conf/license/license-gplv2.inc

SRC_URI = "https://kodi-unlimited-support.de/repo/repository.kus.allinone-${PV}.zip"
SRC_URI[sha256sum] = "2be12cf39706bfb71c5839a17fcdb3c448bb666640a9e51fdeee354ee36f0a00"

inherit allarch

S = "${UNPACKDIR}/repository.kus.allinone"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -d ${D}${datadir}/kodi/addons/repository.kus.allinone
    cp -R --no-preserve=ownership ${S}/. ${D}${datadir}/kodi/addons/repository.kus.allinone/
}

FILES:${PN} = "${datadir}/kodi/addons/repository.kus.allinone"
