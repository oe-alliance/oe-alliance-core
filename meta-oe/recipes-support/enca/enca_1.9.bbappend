SRC_URI = "https://dl.cihar.com/enca/enca-1.9.tar.gz \
        file://configure-hack.patch \
        file://dont-run-tests.patch \
        file://configure-remove-dumbness.patch \
        file://makefile-remove-tools.patch \
        file://libenca-003-iconv.patch "

SRC_URI[md5sum] = "3c95d4141d0c2393b8d6d0aa88302515"
SRC_URI[sha256sum] = "75a38ed23bac37cc12166cc5edc8335c3af862adc202f84823d3aef3e2208e47"
