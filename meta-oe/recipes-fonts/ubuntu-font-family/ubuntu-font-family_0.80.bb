SUMMARY = "The Ubuntu Font Family"
HOMEPAGE = "http://font.ubuntu.com"
LICENSE = "Ubuntu-Font-1.0"
LIC_FILES_CHKSUM = "file://LICENCE.txt;md5=325a1a9029112a2405e743c7f816427b"

SRC_URI = "http://font.ubuntu.com/download/${BPN}-${PV}.zip"
SRC_URI[md5sum] = "a1fc70f5a5b1d096ab8310886cddaa1c"
SRC_URI[sha256sum] = "107170099bbc3beae8602b97a5c423525d363106c3c24f787d43e09811298e4c"

inherit allarch

FONTS = "Ubuntu-B.ttf Ubuntu-BI.ttf Ubuntu-C.ttf Ubuntu-R.ttf Ubuntu-RI.ttf \
         UbuntuMono-B.ttf UbuntuMono-BI.ttf UbuntuMono-R.ttf UbuntuMono-RI.ttf"

do_install(){
    install -d ${D}${libdir}/fonts
    install -m 0644 ${FONTS} ${D}${libdir}/fonts
}

FILES_${PN} = "${libdir}/fonts"
