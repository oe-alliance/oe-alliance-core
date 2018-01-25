SUMMARY = "meta file for enigma2 locales"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = "\
    enigma2-locale-ar \
    enigma2-locale-bg \
    enigma2-locale-ca \
    enigma2-locale-cs \
    enigma2-locale-da \
    enigma2-locale-de \
    enigma2-locale-el \
    enigma2-locale-en \
    enigma2-locale-en-gb \
    enigma2-locale-es \
    enigma2-locale-et \
    enigma2-locale-fa \
    enigma2-locale-fi \
    enigma2-locale-fr \
    enigma2-locale-fy \
    enigma2-locale-he \
    enigma2-locale-hk \
    enigma2-locale-hr \
    enigma2-locale-hu \
    enigma2-locale-id \
    enigma2-locale-is \
    enigma2-locale-it \
    enigma2-locale-ku \
    enigma2-locale-lt \
    enigma2-locale-lv \
    enigma2-locale-nb \
    enigma2-locale-nl \
    enigma2-locale-no \
    enigma2-locale-pl \
    enigma2-locale-pt \
    enigma2-locale-pt-br \
    enigma2-locale-ro \
    enigma2-locale-ru \
    enigma2-locale-sk \
    enigma2-locale-sl \
    enigma2-locale-sr \
    enigma2-locale-sv \
    enigma2-locale-th \
    enigma2-locale-tr \
    enigma2-locale-uk \
    enigma2-locale-zh \
    "

PR = "r00"

ALLOW_EMPTY_${PN} = "1"
